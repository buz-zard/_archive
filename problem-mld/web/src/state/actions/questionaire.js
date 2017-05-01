import api from '../../api';


export const INITIALIZED = 'questionaire/INITIALIZED';
export const METADATA_LOADING_FINISHED = 'questionaire/METADATA_LOADING_FINISHED';
export const QUESTIONS_LOADING_STARTED = 'questionaire/QUESTIONS_LOADING_STARTED';
export const QUESTIONS_LOADING_FINISHED = 'questionaire/QUESTIONS_LOADING_FINISHED';
export const QUESTION_ANSWERED = 'questionaire/QUESTION_ANSWERED';
export const COMPLETED = 'questionaire/COMPLETED';
export const SUBMITTED = 'questionaire/SUBMITTED';


export const initialize = id => ({
  type: INITIALIZED,
  payload: id,
});

export const finishMetadataLoading = ({id, data}) => ({
  type: METADATA_LOADING_FINISHED,
  payload: {id, data},
});

export const startQuestionsLoading = id => ({
  type: QUESTIONS_LOADING_STARTED,
  payload: id,
});

export const finishQuestionsLoading = ({id, questions}) => ({
  type: QUESTIONS_LOADING_FINISHED,
  payload: {id, questions},
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
export const loadMetadata = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {questionaire: {id}} = getState();
  if (id == null) {
    reject();
    return;
  }

  api.getQuestionaire(id).then((response) => {
    dispatch(finishMetadataLoading({id, data: response}));
    resolve();
  }).catch(reject);
});


export const loadQuestions = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {questionaire: {id, questions: {loading, data}}} = getState();
  if (loading || data != null) {
    resolve();
    return;
  }

  dispatch(startQuestionsLoading(id));
  api.getQuestions(id).then((response) => {
    dispatch(finishQuestionsLoading({id, questions: response}));
    resolve();
  }).catch(reject);
});


export const submitAnswers = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {questionaire: {id, answers, completed}} = getState();
  if (!completed) reject();

  api.submitQuestionaire(id, answers).then((response) => {
    dispatch(finishSubmit());
    resolve(response);
  }).catch(reject);
});
