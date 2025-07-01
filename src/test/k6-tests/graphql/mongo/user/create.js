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

const mutation = `
  mutation ($name: String!, $email: String!) {
    createUserDocument(name: $name, email: $email) {
      id
    }
  }
`;

export default function () {
    const uniq     = `${__VU}-${__ITER}`;
    const newName  = `TestUser Created ${uniq}`;
    const newEmail = `testusercreated${uniq}@example.com`;

    const res = http.post(
        BASE_URLS.graphql,
        JSON.stringify({
            query: mutation,
            variables: { name: newName, email: newEmail },
        }),
        { headers: HEADERS.json }
    );

    check(res, {
        'status is 200': (r) => r.status === 200,
        'returned id':   (r) => !!r.json('data.createUserDocument.id'),
    });

    sleep(1);
}
