import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

/* Setup */
export function setup() {
    const query = `query { getAllEmbeddedOrderDocumentIds }`;
    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query }),
        { headers: HEADERS.json }
    );

    const ids = res.json().data?.getAllEmbeddedOrderDocumentIds || [];
    if (ids.length === 0) {
        throw new Error('EmbeddedOrderDocument ID listesi boş!');
    }
    return ids.slice(0, 1200);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const mutation = `
        mutation ($id: ID!) {
          deleteEmbeddedOrderDocument(id: $id)
        }
    `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: mutation, variables: { id: randomId } }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200': (r) => r.status === 200,
        'deleted === true': (r) => r.json('data.deleteEmbeddedOrderDocument') === true,
    });

    sleep(1);
}
