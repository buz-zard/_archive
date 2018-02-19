import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';

import { apiMiddleware } from 'redux-api-middleware';

import { makeAddressKey } from './utils';
import * as actions from './actions';
import * as types from './actionTypes';
import * as selectorsMock from './selectors';

const mockStore = configureMockStore([thunk, apiMiddleware]);

selectorsMock.isGeocodingFetching = jest.fn();
selectorsMock.hasGeocodingFetched = jest.fn();
selectorsMock.getGeocodingError = jest.fn();

const RESPONSE_200_JSON = {
  status: 200,
  headers: {
    'content-type': 'application/json',
  },
};

describe('requestCoordinates', () => {
  beforeEach(() => {
    fetch.resetMocks();
    selectorsMock.isGeocodingFetching.mockReset();
    selectorsMock.hasGeocodingFetched.mockReset();
    selectorsMock.getGeocodingError.mockReset();
  });

  it('should not load if address key is empty', () => {
    const address = {};
    const store = mockStore({});
    const expectedActions = [];

    return store.dispatch(actions.requestCoordinates(address)).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
    });
  });

  it('should not load if it is fetching', () => {
    const address = { city: 'London' };
    const store = mockStore({});
    const expectedActions = [];
    selectorsMock.isGeocodingFetching.mockReturnValueOnce(true);

    return store.dispatch(actions.requestCoordinates(address)).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isGeocodingFetching.mock.calls.length).toBe(1);
    });
  });

  it('should not load if it has successfully fetched', () => {
    const address = { city: 'London' };
    const store = mockStore({});
    const expectedActions = [];
    selectorsMock.isGeocodingFetching.mockReturnValueOnce(false);
    selectorsMock.hasGeocodingFetched.mockReturnValueOnce(true);
    selectorsMock.getGeocodingError.mockReturnValueOnce(null);

    return store.dispatch(actions.requestCoordinates(address)).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isGeocodingFetching.mock.calls.length).toBe(1);
      expect(selectorsMock.hasGeocodingFetched.mock.calls.length).toBe(1);
      expect(selectorsMock.getGeocodingError.mock.calls.length).toBe(1);
    });
  });

  it('should load if it is not fetching', () => {
    const address = { city: 'London', country: 'UK' };
    const MOCKED_RESPONSE = {
      status: 'ok',
      data: { location: 'Right here' },
    };
    const store = mockStore({});
    const expectedActions = [
      {
        type: types.GET_GEOCODING,
        meta: { address: makeAddressKey(address) },
      },
      {
        type: types.GET_GEOCODING_SUCCESS,
        meta: { address: makeAddressKey(address) },
        payload: MOCKED_RESPONSE,
      },
    ];
    selectorsMock.isGeocodingFetching.mockReturnValueOnce(false);
    fetch.mockResponseOnce(JSON.stringify(MOCKED_RESPONSE), RESPONSE_200_JSON);

    return store.dispatch(actions.requestCoordinates(address)).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isGeocodingFetching.mock.calls.length).toBe(1);
    });
  });

  it('should re-load if it is not fetching and previous fetch was failed', () => {
    const address = { city: 'London', country: 'UK' };
    const MOCKED_RESPONSE = {
      status: 'ok',
      data: { location: 'Right here' },
    };
    const store = mockStore({});
    const expectedActions = [
      {
        type: types.GET_GEOCODING,
        meta: { address: makeAddressKey(address) },
      },
      {
        type: types.GET_GEOCODING_SUCCESS,
        meta: { address: makeAddressKey(address) },
        payload: MOCKED_RESPONSE,
      },
    ];
    selectorsMock.isGeocodingFetching.mockReturnValueOnce(false);
    selectorsMock.hasGeocodingFetched.mockReturnValueOnce(true);
    selectorsMock.getGeocodingError.mockReturnValueOnce({});
    fetch.mockResponseOnce(JSON.stringify(MOCKED_RESPONSE), RESPONSE_200_JSON);

    return store.dispatch(actions.requestCoordinates(address)).then(() => {
      expect(store.getActions()).toEqual(expectedActions);
      expect(selectorsMock.isGeocodingFetching.mock.calls.length).toBe(1);
      expect(selectorsMock.hasGeocodingFetched.mock.calls.length).toBe(1);
      expect(selectorsMock.getGeocodingError.mock.calls.length).toBe(1);
    });
  });
});
