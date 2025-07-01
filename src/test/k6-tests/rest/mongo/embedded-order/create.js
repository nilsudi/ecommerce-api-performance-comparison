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
    const r1 = http.get(`${BASE_URLS.restMongo}/users/ids`, {
        headers: HEADERS.json,
    });
    const userIds = r1.json();
    if (!Array.isArray(userIds) || userIds.length === 0) {
        throw new Error('User ID havuzu boş — seed verisi yok mu?');
    }

    const r2 = http.get(`${BASE_URLS.restMongo}/products`, {
        headers: HEADERS.json,
    });
    const products = r2.json();
    if (!Array.isArray(products) || products.length === 0) {
        throw new Error('Product ID havuzu boş — seed verisi yok mu?');
    }
    const productIds = products.map((p) => p.id);

    return {
        userIds:    userIds.slice(0, 50),
        productIds: productIds.slice(0, 50),
    };
}

/* Test */

export default function (data) {
    const { userIds, productIds } = data;

    const userId = userIds[Math.floor(Math.random() * userIds.length)];

    const chosen = Array.from({ length: 3 }, () =>
        productIds[Math.floor(Math.random() * productIds.length)]
    );

    const payload = JSON.stringify({
        userId:     userId,
        productIds: chosen,
    });

    const res = http.post(
        `${BASE_URLS.restMongo}/embedded-orders`,
        payload,
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 201':        (r) => r.status === 201,
        'returned an id':       (r) => !!r.json('id'),
        'latency < 200ms':      (r) => r.timings.duration < 200,
    });

    sleep(1);
}
