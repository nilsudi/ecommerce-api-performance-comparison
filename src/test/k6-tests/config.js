// k6-tests/config.js
export const BASE_URLS = {
    restPostgres: 'http://localhost:8085/api/postgres',
    restMongo: 'http://localhost:8085/api/mongo',
    graphql: 'http://localhost:8085/graphql'
};

export const HEADERS = {
    json: { 'Content-Type': 'application/json' }
};
