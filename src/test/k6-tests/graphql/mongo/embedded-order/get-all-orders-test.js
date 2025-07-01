import http from 'k6/http';
import { check, sleep } from 'k6';
import { BASE_URLS, HEADERS } from '../../../config.js';

export const options = {
    vus: 40,
    duration: '30s',

    summaryTrendStats: ['avg', 'p(95)', 'p(99)'],
    thresholds: {
        http_req_duration: ['p(95)<400', 'p(99)<800'],
        http_req_failed:   ['rate<0.01'],
    },
};

const query = `
query ($page: Int!, $size: Int!) {
  getPaginatedEmbeddedOrderDocuments(page: $page, size: $size) {
    pageNumber
    pageSize
    totalPages
    totalElements
    last
    content {
      id
      totalPrice
      orderDate
      user {
        id
      }
      products {
        id 
      }
    }
  }
}
`;

/* Test */
export default function () {
    const page = Math.floor(Math.random() * 5000) + 1;
    const size = 20;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({ query, variables: { page, size } }),
        { headers: HEADERS.json }   // Content-Type: application/json
    );

    check(res, {
        'status is 200'      : (r) => r.status === 200,
        'latency < 200ms'    : (r) => r.timings.duration < 200,
        'pageNumber matches' : (r) =>
            r.json('data.getPaginatedEmbeddedOrderDocuments.pageNumber') === page,
    });

    sleep(1);
}
