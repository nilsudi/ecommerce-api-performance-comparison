import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

/* Setup */
export function setup() {
    const r1 = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: 'query { getAllUserEntityIds }' }),
        { headers: HEADERS.json }
    );
    const userIds = r1.json().data?.getAllUserEntityIds || [];
    if (userIds.length === 0) {
        throw new Error('User ID havuzu boş!');
    }

    const r2 = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: 'query { getAllProductEntities { id } }' }),
        { headers: HEADERS.json }
    );
    const products = r2.json().data?.getAllProductEntities || [];
    const productIds = products.map(p => p.id);
    if (productIds.length === 0) {
        throw new Error('Product ID havuzu boş!');
    }

    return {
        userIds:    userIds.slice(0, 50),
        productIds: productIds.slice(0, 50),
    };
}

/* Test */
export default function (data) {
    const { userIds, productIds } = data;

    const u = userIds[Math.floor(Math.random() * userIds.length)];
    const ps = Array.from({ length: 3 }, () =>
        productIds[Math.floor(Math.random() * productIds.length)]
    );

    const mutation = `
    mutation ($u: ID!, $ps: [ID!]!) {
      createOrderEntity(
        userId: $u,
        productIds: $ps
      ) {
        id
      }
    }
  `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: mutation, variables: { u, ps } }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200':      (r) => r.status === 200,
        'returned an id':     (r) => r.json('data.createOrderEntity.id') !== undefined,
        'latency < 200ms':    (r) => r.timings.duration < 200,
    });

    sleep(1);
}
