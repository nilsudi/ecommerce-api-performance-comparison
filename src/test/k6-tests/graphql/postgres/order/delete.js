import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

/* Setup */
export function setup() {
    const query = `query { getAllOrderEntityIds }`;
    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query }),
        { headers: HEADERS.json }
    );

    const ids = res.json().data?.getAllOrderEntityIds || [];
    if (ids.length === 0) {
        throw new Error('OrderEntity ID listesi boş!');
    }
    return ids.slice(0, 10000);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const mutation = `
        mutation ($id: ID!) {
          deleteOrderEntity(id: $id)
        }
    `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: mutation, variables: { id: randomId } }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200': (r) => r.status === 200,
        'deleted === true': (r) => r.json('data.deleteOrderEntity') === true,
    });

    sleep(1);
}
