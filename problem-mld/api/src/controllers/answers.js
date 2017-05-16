import {answers} from '../services';


export default {
  submitAnswer(req, res, next) {
    answers.submitAnswer(parseInt(req.params.questionaireId, 10), req.body).then((response) => {
      res.json(response);
    }).catch(next);
  },
};
