import _ from 'lodash';

import cfg from './config';


const isDevelopment = process.env.NODE_ENV !== 'development';
const baseUrl = isDevelopment ? '' : 'http://api.dribbble.com/';


const query = (params = {}) => {
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
  const $query = _.get(options, 'query', {});

  if (!isDevelopment) {
    params.mode = 'cors';
    params.headers['Access-Control-Allow-Origin'] = '';
    params.headers['Access-Control-Request-Method'] = 'GET';
  }

  return new Promise((resolve, reject) => {
    fetch(`${baseUrl}/v1${url}${query({access_token: cfg.ACCESS_TOKEN, ...$query})}`, params)
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
