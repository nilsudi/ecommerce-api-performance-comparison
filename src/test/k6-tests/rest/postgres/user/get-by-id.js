import http   from 'k6/http';
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
    const res = http.get(`${BASE_URLS.restPostgres}/users/ids`, {
        headers: HEADERS.json,
    });

    const ids = res.json();

    if (!ids || ids.length === 0) {
        throw new Error('User ID listesi boş — seed verisi yok mu?');
    }
    return ids.slice(0, 50);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const res = http.get(`${BASE_URLS.restPostgres}/users/${randomId}`, {
        headers: HEADERS.json,
    });

    check(res, {
        'status is 200'    : (r) => r.status === 200,
        'latency < 200ms'  : (r) => r.timings.duration < 200,
        'correct document' : (r) => String(r.json('id')) === String(randomId),
    });

    sleep(1);
}
