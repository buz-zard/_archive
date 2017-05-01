import fixtures from '../fixtures';


export default {
  getList() {
    return Promise.resolve(fixtures.questionaires);
  },
  getOne(id) {
    const questionaire = fixtures.questionaires.find(item => item.id === id);
    if (questionaire != null) {
      return Promise.resolve(questionaire);
    }
    return Promise.reject();
  },
};
