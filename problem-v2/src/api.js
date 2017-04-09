import cfg from './config';


const call = (method, url) => {
  const params = {method};

  return new Promise((resolve, reject) => {
    fetch(`/v1${url}?access_token=${cfg.ACCESS_TOKEN}`, params)
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
  getShots() {
    return call('get', '/shots');
  },
};
