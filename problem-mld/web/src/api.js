const baseUrl = '/api';


const call = (method, url, options = {}) => {
  const params = {
    method,
    headers: {
      Accept: 'application/json',
    },
  };
  if (options.body != null) {
    params.headers['Content-Type'] = 'application/json';
    params.body = JSON.stringify(options.body);
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
  getQuestionaires() {
    return call('get', 'questionaires');
  },
  getQuestionaire(questionaireId) {
    return call('get', `questionaires/${questionaireId}`);
  },
  getQuestions(questionaireId) {
    return call('get', `questionaires/${questionaireId}/questions`);
  },
  getChoices(questionId) {
    return call('get', `questionaires/questions/${questionId}/choices`);
  },
  submitQuestionaire(questionaireId, answers) {
    return call('post', `questionaires/${questionaireId}/answers`, {body: answers});
  },
};
