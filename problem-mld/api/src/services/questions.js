import {Question, Choice} from '../models';


export default {
  getListByQuestionaire(id) {
    return Question.findAll({
      where: {questionaireId: id},
      order: [['order', 'ASC']],
      attributes: ['id', 'name', 'type'],
    });
  },
  getChoicesByQuestionId(id) {
    return Choice.findAll({
      where: {questionId: id},
      order: [['order', 'ASC']],
      attributes: ['id', 'name'],
    });
  },
};
