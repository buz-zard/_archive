module.exports = {
  development: {
    dialect: 'sqlite',
    storage: 'db.sqlite',
  },
  test: {
    dialect: 'sqlite',
    storage: ':memory:',
  },
  production: {
    dialect: 'postgres',
    username: process.env.RDS_USERNAME || '',
    password: process.env.RDS_PASSWORD || '',
    database: process.env.RDS_DB_NAME || 'startup_quotes',
    host: process.env.RDS_HOSTNAME || '',
    port: process.env.RDS_PORT || '5432',
  },
};
