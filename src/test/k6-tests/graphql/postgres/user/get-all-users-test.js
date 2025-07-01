import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 40,
    duration: '30s',
};

const query = `
  query {
    getAllUserEntities {
      id
      name
      email
    }
  }
`;

export default function () {
    const res = http.post(BASE_URLS.graphql, JSON.stringify({ query }), {
        headers: HEADERS.json,
    });

    check(res, {
        'status is 200': (r) => r.status === 200,
        'latency < 200ms': (r) => r.timings.duration < 200,
        'response has data': (r) => r.json('data.getAllUserEntities') !== undefined,
    });

    sleep(1);
}
