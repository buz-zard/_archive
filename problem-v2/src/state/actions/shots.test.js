import thunk from 'redux-thunk';
import configureMockStore from 'redux-mock-store';

import mockApi from '../../api';
import * as action from './shots';


jest.mock('../../api', () => ({
  getShots: jest.fn(),
}));

const middlewares = [thunk];
const mockStore = configureMockStore(middlewares);


describe('loadItems', () => {
  afterEach(() => {
    mockApi.getShots.mockReset();
  });

  it('shouldn\'t load if loading is in progress', () => {
    const expectedActions = [];
    const store = mockStore({shots: {
      list: {loading: true},
    }});

    return store.dispatch(action.loadItems())
      .then(() => {
        expect(store.getActions()).toEqual(expectedActions);
      });
  });


  it('shouldn\'t load if shots list isn\'t initialized', () => {
    const expectedActions = [];
    const store = mockStore({shots: {
      list: {loading: false, pageSize: null},
    }});

    return store.dispatch(action.loadItems())
      .then(() => {
        expect(store.getActions()).toEqual(expectedActions);
      });
  });


  it('should load with right conditions', () => {
    const shots = [{
      id: 1, html_url: 'www1', title: '1', images: {teaser: '1.png'},
    }, {
      id: 2, html_url: 'www2', title: '2', images: {teaser: '2.png'},
    }];
    const expectedShots = [{
      id: 1, url: 'www1', title: '1', image: '1.png',
    }, {
      id: 2, url: 'www2', title: '2', image: '2.png',
    }];
    const store = mockStore({shots: {
      list: {loading: false, pageSize: 10, page: 0},
    }});
    const expectedActions = [{
      type: action.ITEMS_LOADING_STARTED,
      payload: {page: 1},
    }, {
      type: action.ITEMS_LOADING_FINISHED,
      payload: {page: 1, data: expectedShots},
    }];

    mockApi.getShots.mockReturnValueOnce(Promise.resolve(shots));

    return store.dispatch(action.loadItems())
      .then(() => {
        expect(mockApi.getShots.mock.calls.length).toBe(1);
        expect(mockApi.getShots.mock.calls[0]).toEqual([1, 10]);
        expect(store.getActions()).toEqual(expectedActions);
      });
  });
});
