import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 50,
    duration: '30s',
};

/* Setup */
export function setup() {
    const res = http.get(`${BASE_URLS.restPostgres}/orders/ids`, {
        headers: HEADERS.json,
    });

    const ids = res.json();
    if (!ids || ids.length === 0) {
        throw new Error('Order ID listesi boş – seed verisi yok mu?');
    }
    return ids.slice(0, 1200);
}

/* Test */
export default function (ids) {
    const randomId = ids[Math.floor(Math.random() * ids.length)];

    const res = http.del(
        `${BASE_URLS.restPostgres}/orders/${randomId}`,
        null,
        { headers: HEADERS.json }
    );

    check(res, {
        '204 veya 404': (r) => r.status === 204 || r.status === 404,
    });

    sleep(1);
}
