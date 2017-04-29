import reducer from './questionaire';
import * as types from '../actions/questionaire';


// ########################################################################
// Questions
// ########################################################################
describe('questions', () => {
  it('sets loading and resets answers', () => {
    expect(reducer({
      questions: {},
      answers: [{
        id: 1,
        value: 2,
      }],
    }, {
      type: types.QUESTIONS_LOADING_STARTED,
    })).toEqual({
      questions: {
        loading: true,
        data: null,
      },
      answers: [],
    });
  });

  it('finises loading', () => {
    expect(reducer({
      questions: {loading: true},
    }, {
      type: types.QUESTIONS_LOADING_FINISHED,
      payload: [{id: 33}],
    })).toEqual({
      questions: {
        loading: false,
        data: [{id: 33}],
      },
    });
  });
});
