import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

export function setup() {
    const ordersRes = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: `query { getAllOrderEntityIds }` }),
        { headers: HEADERS.json }
    );
    let orderIds = ordersRes.json('data.getAllOrderEntityIds') || [];
    if (!orderIds.length) {
        throw new Error('Order ID havuzu boş');
    }

    const productsRes = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: `query { getAllProductEntityIds }` }),
        { headers: HEADERS.json }
    );
    let productIds = productsRes.json('data.getAllProductEntityIds') || [];
    if (!productIds.length) {
        throw new Error('Product ID havuzu boş');
    }

    const usersRes = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: `query { getAllUserEntityIds }` }),
        { headers: HEADERS.json }
    );

    let userIds = usersRes.json('data.getAllUserEntityIds') || [];
    if (!userIds.length) {
        throw new Error('User ID havuzu boş');
    }

    orderIds = orderIds.slice(0, 50);
    productIds = productIds.slice(0, 50);
    userIds = userIds.slice(0, 50);

    return { orderIds, productIds, userIds };
}

export default function (data) {
    const { orderIds, productIds, userIds } = data;

    const id = orderIds[Math.floor(Math.random() * orderIds.length)];
    const userId = userIds[Math.floor(Math.random() * userIds.length)];

    const newProductIds = [];
    const count = Math.ceil(Math.random() * 5);
    for (let i = 0; i < count; i++) {
        newProductIds.push(
            productIds[Math.floor(Math.random() * productIds.length)]
        );
    }

    const mutation = `
        mutation Update($id: ID!, $userId: ID!, $productIds: [ID!]!) {
            updateOrderEntity(
                id: $id,
                userId: $userId,
                productIds: $productIds
            ) {
                id
                totalPrice
                orderDate
            }
        }
    `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({
            query: mutation,
            variables: { id, userId, productIds: newProductIds },
        }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status 200': (r) => r.status === 200,
        'no graphql errors': (r) => !r.json().errors,
        'same id returned': (r) =>
            r.json('data.updateOrderEntity.id') === id,
        'totalPrice > 0': (r) =>
            parseFloat(r.json('data.updateOrderEntity.totalPrice')) > 0,
        'orderDate present': (r) =>
            Boolean(r.json('data.updateOrderEntity.orderDate')),
    });

    sleep(1);
}
