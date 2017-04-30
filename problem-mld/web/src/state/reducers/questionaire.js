import update from 'immutability-helper';

import {
  QUESTIONS_LOADING_STARTED, QUESTIONS_LOADING_FINISHED,
  QUESTION_ANSWERED, COMPLETED, SUBMITTED,
} from '../actions/questionaire';


const DEFAULT_STATE = {
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
    case QUESTIONS_LOADING_STARTED:
      return update(state, {
        $merge: {
          questions: {
            ...DEFAULT_STATE.questions,
            loading: true,
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
            currentIndex: 0,
          },
        },
      });
    case QUESTION_ANSWERED: {
      const {currentIndex, data: questions} = state.questions;
      const question = questions[currentIndex];
      if (question.type === 'single'
        && !Array.isArray(payload) // Answer is single value
        && payload != null
        && state.answers.find(item => item.questionId === question.id) == null // Answer isn't answered
        && question.options.find(item => item.value === payload) != null) { // Answer is from options
        const answer = {questionId: question.id, answer: payload};
        return update(state, {
          questions: {currentIndex: {
            $set: currentIndex < questions.length - 1 ? currentIndex + 1 : currentIndex,
          }},
          answers: {$push: [answer]},
        });
      } else if (question.type === 'multi'
        && Array.isArray(payload) // Answer is array
        && payload.length > 0 // Array is not empty
        && state.answers.find(item => item.questionId === question.id) == null) { // Answer isn't answered
        let answerIsValid = true;
        payload.forEach((_answer) => { // Answer is from options
          if (!(_answer != null && question.options.find(_option => _option.value === _answer) != null)) {
            answerIsValid = false;
          }
        });
        if (answerIsValid) {
          const answer = {questionId: question.id, answer: payload};
          return update(state, {
            questions: {currentIndex: {
              $set: currentIndex < questions.length - 1 ? currentIndex + 1 : currentIndex,
            }},
            answers: {$push: [answer]},
          });
        }
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
