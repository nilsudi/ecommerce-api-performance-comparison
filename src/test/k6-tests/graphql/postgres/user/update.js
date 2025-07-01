import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = { vus: 50, duration: '30s' };

/* Setup */
export function setup() {
    const idQuery = `query { getAllUserEntityIds }`;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query: idQuery }),
        { headers: HEADERS.json }
    );

    const ids = res.json().data?.getAllUserEntityIds ?? [];
    if (ids.length === 0) { throw new Error('UserEntity ID listesi boş'); }

    return ids.slice(0, 50);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const uniq     = `${__VU}-${__ITER}`;
    const newName  = `Test Name ${uniq}`;
    const newEmail = `test-${uniq}@example.com`;

    const mutation = `
    mutation ($id: ID!, $n: String!, $e: String!) {
      updateUserEntity(id: $id, name: $n, email: $e) {
        id
      }
    }
  `;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({
            query: mutation,
            variables: { id: randomId, n: newName, e: newEmail }
        }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status 200'       : (r) => r.status === 200,
        'latency < 200ms'  : (r) => r.timings.duration < 200,
        'same id returned' : (r) =>
            String(r.json('data.updateUserEntity.id')) === String(randomId),
    });

    sleep(1);
}
