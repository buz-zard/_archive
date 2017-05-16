import {validateAnswer} from '../utils';
import {questionaires} from './';

export default {
  submitAnswer(questionaireId, answers) {
    return new Promise((resolve, reject) => {
      questionaires.getSolution(questionaireId)
        .then(data => resolve(validateAnswer(answers, data.questions)))
        .catch(reject);
    });
  },
};
