export default {
  submitAnswer(questionaireId, answers) {
    return Promise.resolve({mistakes: 1, questionaireId, answers});
  },
};
