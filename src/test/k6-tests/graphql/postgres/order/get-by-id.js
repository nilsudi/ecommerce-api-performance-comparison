import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

/* Setup */
export function setup() {
    const idQuery = `query { getAllOrderEntityIds }`;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: idQuery }),
        { headers: HEADERS.json }
    );

    const ids = res.json().data.getAllOrderEntityIds;

    if (!ids || ids.length === 0) {
        throw new Error('OrderEntity kimliği bulunamadı');
    }
    return ids.slice(0, 50);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const query = `
    query ($id: ID!) {
      getOrderEntityById(id: $id) {
        id
        totalPrice
        orderDate
        user   { id name email }
        products { id name price }
      }
    }
  `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query, variables: { id: randomId } }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200'      : (r) => r.status === 200,
        'latency < 200ms'    : (r) => r.timings.duration < 200,
        'correct document': (r) =>
            String(r.json('data.getOrderEntityById.id')) === String(randomId),
    });

    sleep(1);
}
