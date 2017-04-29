import thunk from 'redux-thunk';
import configureMockStore from 'redux-mock-store';

import mockApi from '../../api';
import * as action from './questionaire';


jest.mock('../../api', () => ({
  getQuestions: jest.fn(),
}));

const mockStore = configureMockStore([thunk]);


describe('loadQuestions', () => {
  afterEach(() => {
    mockApi.getQuestions.mockReset();
  });

  it('shouldn\'t load if loading is in progress', () => {
    const expectedActions = [];
    const store = mockStore({questionaire: {
      questions: {loading: true},
    }});

    return store.dispatch(action.loadQuestions())
      .then(() => {
        expect(store.getActions()).toEqual(expectedActions);
      });
  });


  it('shouldn\'t load if questions if data already loaded', () => {
    const expectedActions = [];
    const store = mockStore({questionaire: {
      questions: {loading: false, data: [{}]},
    }});

    return store.dispatch(action.loadQuestions())
      .then(() => {
        expect(store.getActions()).toEqual(expectedActions);
      });
  });


  it('should load with right conditions', () => {
    const questions = [{id: 1}, {id: 2}];

    const store = mockStore({questionaire: {
      questions: {loading: false},
    }});
    const expectedActions = [{
      type: action.QUESTIONS_LOADING_STARTED,
    }, {
      type: action.QUESTIONS_LOADING_FINISHED,
      payload: questions,
    }];

    mockApi.getQuestions.mockReturnValueOnce(Promise.resolve(questions));

    return store.dispatch(action.loadQuestions())
      .then(() => {
        expect(mockApi.getQuestions.mock.calls.length).toBe(1);
        expect(store.getActions()).toEqual(expectedActions);
      });
  });
});
