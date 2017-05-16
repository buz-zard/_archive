import _ from 'lodash';


export const validateAnswer = (answer, solution) => {
  if (!(Array.isArray(answer) && answer.length && answer.length === solution.length)) {
    throw new Error('Invalid answer object');
  }

  let mistakes = 0;
  answer.forEach((answerItem) => {
    if (!_.isPlainObject(answerItem)) throw new Error('Invalid answer object');
    const {questionId, answer: answerValue} = answerItem;

    const solutionQuestion = solution.find(item => item.id === questionId);
    if (solutionQuestion == null) throw new Error(`Question with id [${questionId}] not found.`);

    const solutionAnswerValue = _.get(solutionQuestion, 'choices[0].id');
    if (answerValue !== solutionAnswerValue) mistakes += 1;
  });
  return {mistakes};
};
