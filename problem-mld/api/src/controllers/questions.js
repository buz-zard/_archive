import {questions} from '../services';


export default {
  getListForQuestionaire(req, res, next) {
    questions.getListByQuestionaire(parseInt(req.params.questionaireId, 10)).then((response) => {
      res.json(response);
    }).catch(next);
  },
  getChoicesListForQuestion(req, res, next) {
    questions.getChoicesByQuestionId(parseInt(req.params.questionId, 10)).then((response) => {
      res.json(response);
    }).catch(next);
  },
};

