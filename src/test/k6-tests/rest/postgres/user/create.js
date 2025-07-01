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

export default function () {
    const uniq     = `${__VU}-${__ITER}`;
    const newName  = `TestUser REST ${uniq}`;
    const newEmail = `restuser${uniq}@example.com`;

    const res = http.post(
        `${BASE_URLS.restPostgres}/users`,
        JSON.stringify({ name: newName, email: newEmail }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200':   (r) => r.status === 200,
        'returned id':     (r) => !!r.json('id'),
        'latency < 200ms': (r) => r.timings.duration < 200,
    });

    sleep(1);
}
