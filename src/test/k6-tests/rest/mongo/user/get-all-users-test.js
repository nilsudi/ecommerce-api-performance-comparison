import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export let options = {
    vus: 40,
    duration: '30s',
};

export default function () {
    const res = http.get(`${BASE_URLS.restMongo}/users`, { headers: HEADERS.json });

    check(res, {
        'status is 200': (r) => r.status === 200,
        'latency < 200ms': (r) => r.timings.duration < 200,
    });

    sleep(1);
}
