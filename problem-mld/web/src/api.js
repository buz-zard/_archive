import fixtures from './fixtures';

const baseUrl = '/api';


const call = (method, url, options = {}) => {
  const params = {
    method,
    headers: {
      Accept: 'application/json',
    },
  };
  if (options.data != null) {
    params.headers.Content = 'application/json';
    params.body = JSON.stringify(options.data);
  }

  return new Promise((resolve, reject) => {
    fetch(`${baseUrl}/${url}`, params)
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
  getQuestions() {
    // return call('get', 'questions');
    return new Promise(resolve => setTimeout(() => resolve(fixtures.questions), 500));
  },
  submitQuestionaire(answers) {
    return call('post', 'questionaires/answers', {body: answers});
    // return new Promise(resolve => setTimeout(() => resolve(), 500));
  },
};
