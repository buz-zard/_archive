import {questions} from '../services';


const onError = res => err => res.status(500).send(err);


export default {
  getListForQuestionaire(req, res) {
    questions.getListByQuestionaire(parseInt(req.params.questionaireId, 10)).then((response) => {
      res.json(response);
    }).catch(onError(res));
  },
  getChoicesListForQuestion(req, res) {
    questions.getChoicesByQuestionId(parseInt(req.params.questionId, 10)).then((response) => {
      res.json(response);
    }).catch(onError(res));
  },
};

