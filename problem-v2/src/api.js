import _ from 'lodash';

import cfg from './config';


const isDevelopment = process.env.NODE_ENV === 'development';
const baseUrl = isDevelopment ? '' : 'https://api.dribbble.com';


const makeQuery = (params = {}) => {
  const result = Object.keys(params)
    .filter(key => params[key] != null && params[key] !== '')
    .map(key => `${key}=${String(params[key])}`);
  return result.length ? `?${result.join('&')}` : '';
};


const call = (method, url, options = {}) => {
  const params = {
    method,
    headers: {
      Accept: 'application/json',
    },
  };
  const queryArgs = _.get(options, 'query', {});

  if (!isDevelopment) {
    params.mode = 'cors';
  }

  return new Promise((resolve, reject) => {
    fetch(`${baseUrl}/v1${url}${makeQuery({access_token: cfg.ACCESS_TOKEN, ...queryArgs})}`, params)
      .then((res) => {
        if (res.ok) {
          res.json().then(resolve).catch(reject);
        } else {
          reject(res.body);
        }
      })
      .catch(reject);
  });
};


export default {
  getShots(page, perPage) {
    return call('get', '/shots', {query: {page, per_page: perPage}});
  },
};
