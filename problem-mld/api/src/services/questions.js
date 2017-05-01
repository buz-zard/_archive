import fixtures from '../fixtures';


export default {
  getListByQuestionaire(id) {
    const questions = fixtures.questions.find(item => item.questionaireId === id);
    if (questions != null) {
      return Promise.resolve(questions.data);
    }
    return Promise.reject();
  },
};
