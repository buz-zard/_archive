import update from 'immutability-helper';

import {
  INITIALIZED,
  METADATA_LOADING_FINISHED,
  QUESTIONS_LOADING_STARTED, QUESTIONS_LOADING_FINISHED,
  QUESTION_ANSWERED, COMPLETED, SUBMITTED,
} from '../actions/questionaire';


const DEFAULT_STATE = {
  id: null,
  info: null,
  questions: {
    loading: false,
    data: null,
    currentIndex: null,
  },
  answers: [],
  completed: false,
};


export default (state = DEFAULT_STATE, {type, payload}) => {
  switch (type) {
    case INITIALIZED:
      if (payload != null) {
        return {
          ...DEFAULT_STATE,
          id: payload,
        };
      }
      return DEFAULT_STATE;
    case METADATA_LOADING_FINISHED:
      if (payload.id != null && state.id === payload.id) {
        return update(state, {
          $merge: {
            info: payload.data,
          },
        });
      }
      return state;
    case QUESTIONS_LOADING_STARTED:
      if (payload != null && state.id === payload) {
        return update(state, {
          $merge: {
            questions: {
              ...DEFAULT_STATE.questions,
              loading: true,
            },
          },
        });
      }
      return state;
    case QUESTIONS_LOADING_FINISHED:
      if (payload.id != null && state.id === payload.id) {
        return update(state, {
          questions: {
            $set: {
              loading: false,
              data: payload.questions,
              currentIndex: 0,
            },
          },
        });
      }
      return state;
    case QUESTION_ANSWERED: {
      const {currentIndex, data: questions} = state.questions;
      const question = questions[currentIndex];
      if (question.type === 'SINGLE'
        && !Array.isArray(payload) // Answer is SINGLE value
        && payload != null
        && state.answers.find(item => item.questionId === question.id) == null) { // Answer isn't answered
        const answer = {questionId: question.id, answer: payload};
        return update(state, {
          questions: {currentIndex: {
            $set: currentIndex < questions.length - 1 ? currentIndex + 1 : currentIndex,
          }},
          answers: {$push: [answer]},
        });
      } else if (question.type === 'MULTI'
        && Array.isArray(payload) // Answer is array
        && payload.length > 0 // Array is not empty
        && state.answers.find(item => item.questionId === question.id) == null) { // Answer isn't answered
        const answer = {questionId: question.id, answer: payload};
        return update(state, {
          questions: {currentIndex: {
            $set: currentIndex < questions.length - 1 ? currentIndex + 1 : currentIndex,
          }},
          answers: {$push: [answer]},
        });
      }
      return state;
    }
    case COMPLETED:
      if (state.questions.data.length === state.answers.length) {
        return update(state, {
          completed: {
            $set: true,
          },
        });
      }
      return state;
    case SUBMITTED:
      if (state.completed) {
        return update(state, {
          questions: {currentIndex: {
            $set: 0,
          }},
          answers: {
            $set: DEFAULT_STATE.answers,
          },
          completed: {
            $set: DEFAULT_STATE.completed,
          },
        });
      }
      return state;
    default:
      return state;
  }
};
