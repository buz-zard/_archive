import _ from 'lodash';

import cfg from './config';


const query = (params = {}) => {
  const result = Object.keys(params)
    .filter(key => params[key] != null && params[key] !== '')
    .map(key => `${key}=${String(params[key])}`);
  return result.length ? `?${result.join('&')}` : '';
};


const call = (method, url, options = {}) => {
  const params = {method};
  const $query = _.get(options, 'query', {});

  return new Promise((resolve, reject) => {
    fetch(`/v1${url}${query({access_token: cfg.ACCESS_TOKEN, ...$query})}`, params)
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
