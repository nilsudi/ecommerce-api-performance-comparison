import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 100,
    duration: '30s',
};

/* Setup */
export function setup() {
    const res = http.get(
        `${BASE_URLS.restMongo}/users/ids`,
        { headers: HEADERS.json }
    );

    const ids = res.json();

    if (!Array.isArray(ids) || !ids.length) {
        throw new Error('User ID listesi boş – seed verisi yok mu?');
    }

    return ids.slice(0, 1200);
}

/* Test */
export default function (ids) {
    const index = Math.floor(Math.random() * ids.length);
    const userId = ids[index];

    const res = http.del(
        `${BASE_URLS.restMongo}/users/${userId}`,
        null,
        { headers: HEADERS.json }
    );

    if (res.status === 204) {
        ids.splice(index, 1);
    }

    check(res, {
        '204 veya 404': (r) => r.status === 204 || r.status === 404,
    });

    sleep(1);
}
