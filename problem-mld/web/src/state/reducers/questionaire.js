import update from 'immutability-helper';

import {QUESTIONS_LOADING_STARTED, QUESTIONS_LOADING_FINISHED} from '../actions/questionaire';


const DEFAULT_STATE = {
  questions: {
    loading: false,
    data: null,
  },
  answers: [],
};


export default (state = DEFAULT_STATE, {type, payload}) => {
  switch (type) {
    case QUESTIONS_LOADING_STARTED:
      return update(state, {
        $merge: {
          questions: {
            loading: true,
            data: DEFAULT_STATE.questions.data,
          },
          answers: DEFAULT_STATE.answers,
        },
      });
    case QUESTIONS_LOADING_FINISHED:
      return update(state, {
        questions: {
          $set: {
            loading: false,
            data: payload,
          },
        },
      });
    default:
      return state;
  }
};
