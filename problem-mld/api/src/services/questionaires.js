import fixtures from '../fixtures';

const Questionaire = require('../models').Questionaire;


export default {
  getList() {
    return Questionaire.findAll({order: [['order', 'ASC']]});
  },
  getOne(id) {
    const questionaire = fixtures.questionaires.find(item => item.id === id);
    if (questionaire != null) {
      return Promise.resolve(questionaire);
    }
    return Promise.reject();
  },
};
