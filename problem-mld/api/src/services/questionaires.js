import {Questionaire, Question, Choice} from '../models';


export default {
  getList() {
    return Questionaire.findAll({order: [['order', 'ASC']], attributes: ['id', 'name']});
  },
  getOne(id) {
    return Questionaire.findById(id, {attributes: ['id', 'name']});
  },
  getSolution(id) {
    return Questionaire.findById(id, {
      attributes: [],
      include: [{
        model: Question,
        as: 'questions',
        attributes: ['id', 'name', 'type'],
        include: [{
          model: Choice,
          as: 'choices',
          attributes: ['id', 'name'],
          where: {isCorrect: true},
        }],
      }],
    });
  },
};
