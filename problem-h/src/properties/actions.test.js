import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';

import * as actions from './actions';
import * as types from './actionTypes';
import * as selectorsMock from './selectors';
import * as utilsMock from './utils';

const mockStore = configureMockStore([thunk]);

selectorsMock.isFetching = jest.fn();
selectorsMock.hasFetched = jest.fn();
selectorsMock.getError = jest.fn();
utilsMock.getProperties = jest.fn();

describe('requestData', () => {
  beforeEach(() => {
    fetch.resetMocks();
    selectorsMock.isFetching.mockReset();
    selectorsMock.hasFetched.mockReset();
    selectorsMock.getError.mockReset();
    utilsMock.getProperties.mockReset();
  });

  it('should not load if it is fetching', () => {
    const store = mockStore({});
    const expectedActions = [];
    selectorsMock.isFetching.mockReturnValueOnce(true);

    return store.dispatch(actions.requestData()).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isFetching.mock.calls.length).toBe(1);
    });
  });

  it('should not load if it has successfully fetched', () => {
    const store = mockStore({});
    const expectedActions = [];
    selectorsMock.isFetching.mockReturnValueOnce(false);
    selectorsMock.hasFetched.mockReturnValueOnce(true);
    selectorsMock.getError.mockReturnValueOnce(null);

    return store.dispatch(actions.requestData()).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isFetching.mock.calls.length).toBe(1);
      expect(selectorsMock.hasFetched.mock.calls.length).toBe(1);
      expect(selectorsMock.getError.mock.calls.length).toBe(1);
    });
  });

  it('should load if it is not fetching', () => {
    const MOCKED_RESPONSE = [{ id: 1 }, { id: 2 }];
    const store = mockStore({});
    const expectedActions = [
      {
        type: types.GET_DATA,
      },
      {
        type: types.GET_DATA_SUCCESS,
        payload: MOCKED_RESPONSE,
      },
    ];
    selectorsMock.isFetching.mockReturnValueOnce(false);
    utilsMock.getProperties.mockReturnValueOnce(MOCKED_RESPONSE);

    return store.dispatch(actions.requestData()).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isFetching.mock.calls.length).toBe(1);
    });
  });

  it('should re-load if it is not fetching and previous fetch was failed', () => {
    const MOCKED_RESPONSE = [{ id: 1 }, { id: 2 }];
    const store = mockStore({});
    const expectedActions = [
      {
        type: types.GET_DATA,
      },
      {
        type: types.GET_DATA_SUCCESS,
        payload: MOCKED_RESPONSE,
      },
    ];
    selectorsMock.isFetching.mockReturnValueOnce(false);
    selectorsMock.hasFetched.mockReturnValueOnce(true);
    selectorsMock.getError.mockReturnValueOnce({});
    utilsMock.getProperties.mockReturnValueOnce(MOCKED_RESPONSE);

    return store.dispatch(actions.requestData()).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isFetching.mock.calls.length).toBe(1);
      expect(selectorsMock.hasFetched.mock.calls.length).toBe(1);
      expect(selectorsMock.getError.mock.calls.length).toBe(1);
    });
  });
});
