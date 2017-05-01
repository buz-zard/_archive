import {answers} from '../services';


const onError = res => err => res.status(500).send(err);


export default {
  submitAnswer(req, res) {
    answers.submitAnswer(parseInt(req.params.questionaireId, 10), req.body).then((response) => {
      res.json(response);
    }).catch(onError(res));
  },
};
