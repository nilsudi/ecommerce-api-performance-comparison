import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
    thresholds: {
        http_req_duration: ['p(95)<200'],
    },
};

/* Setup */
export function setup() {
    const res = http.get(
        `${BASE_URLS.restMongo}/users/ids`,
        { headers: HEADERS.json }
    );
    const ids = res.json();
    if (!Array.isArray(ids) || ids.length === 0) {
        throw new Error('UserDocument ID listesi boş – seed verisi yok mu?');
    }
    return ids.slice(0, 50);
}

/* Test */
export default function (ids) {
    const id = ids[Math.floor(Math.random() * ids.length)];

    const uniq     = `${__VU}-${__ITER}`;
    const newName  = `Test Name ${uniq}`;
    const newEmail = `test-${uniq}@example.com`;

    const url     = `${BASE_URLS.restMongo}/users/${id}`;
    const payload = JSON.stringify({ name: newName, email: newEmail });
    const res     = http.put(url, payload, { headers: HEADERS.json });

    check(res, {
        'status is 200'       : (r) => r.status === 200,
        'latency < 200ms'     : (r) => r.timings.duration < 200,
        'correct id returned' : (r) => String(r.json('id')) === String(id),
    });

    sleep(1);
}
