import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export let options = {
    vus: 40,
    duration:"30s",
    summaryTrendStats: ['avg', 'p(95)', 'p(99)'],

    thresholds: {
        http_req_duration: ['p(95)<400', 'p(99)<800'],
        http_req_failed:   ['rate<0.01'],
    },
};

export default function () {
    const res = http.get(`${BASE_URLS.restPostgres}/orders/getPaginatedOrders?page=${Math.floor(Math.random() * 5000) + 1}`, { headers: HEADERS.json });
    check(res, {
        'status is 200': (r) => r.status === 200,
        'latency < 200ms': (r) => r.timings.duration < 200,
    });

    sleep(1);
}
