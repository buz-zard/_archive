import api from '../../api';


export const QUESTIONS_LOADING_STARTED = 'questionaire/QUESTIONS_LOADING_STARTED';
export const QUESTIONS_LOADING_FINISHED = 'questionaire/QUESTIONS_LOADING_FINISHED';
export const QUESTION_ANSWERED = 'questionaire/QUESTION_ANSWERED';
export const COMPLETED = 'questionaire/COMPLETED';
export const SUBMITTED = 'questionaire/SUBMITTED';


export const startQuestionsLoading = () => ({
  type: QUESTIONS_LOADING_STARTED,
});

export const finishQuestionsLoading = data => ({
  type: QUESTIONS_LOADING_FINISHED,
  payload: data,
});

export const answerQuestion = answer => ({
  type: QUESTION_ANSWERED,
  payload: answer,
});

export const completeQuestionaire = () => ({
  type: COMPLETED,
});

export const finishSubmit = () => ({
  type: SUBMITTED,
});


// async
export const loadQuestions = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {questionaire: {questions: {loading, data}}} = getState();
  if (loading || data != null) {
    resolve();
    return;
  }

  dispatch(startQuestionsLoading());
  api.getQuestions().then((response) => {
    dispatch(finishQuestionsLoading(response));
    resolve();
  }).catch(reject);
});


export const submitAnswers = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {questionaire: {answers, completed}} = getState();
  if (!completed) reject();

  api.submitQuestionaire(answers).then((response) => {
    dispatch(finishSubmit());
    resolve(response);
  }).catch(reject);
});
