import reducer from './reducer';
import * as types from './actionTypes';

describe('geocoding', () => {
  it('should initialize on fething start', () => {
    const key = 'address 1';
    const geocoding = {
      'a b': { val: 1 },
    };
    expect(
      reducer(
        { geocoding },
        {
          type: types.GET_GEOCODING,
          meta: { address: key },
        }
      )
    ).toEqual({
      geocoding: {
        ...geocoding,
        [key]: {
          fetching: true,
          fetched: false,
          data: null,
          error: null,
        },
      },
    });
  });

  it('should handle fething success', () => {
    const key = 'address 1';
    const geocoding = {
      [key]: {
        fetching: true,
        fetched: false,
        data: null,
        error: null,
      },
      2: { val: 4 },
    };
    const payload = { status: 'ok', data: [] };
    expect(
      reducer(
        { geocoding },
        {
          type: types.GET_GEOCODING_SUCCESS,
          meta: { address: key },
          payload,
        }
      )
    ).toEqual({
      geocoding: {
        ...geocoding,
        [key]: {
          fetching: false,
          fetched: true,
          data: payload,
          error: null,
        },
      },
    });
  });

  it('should handle fething failure', () => {
    const key = 'address 1';
    const geocoding = {
      [key]: {
        fetching: true,
        fetched: false,
        data: null,
        error: null,
      },
      2: { val: 4 },
    };
    const payload = { oh: 'ooo', reason: [1, 2, 3] };
    expect(
      reducer(
        { geocoding },
        {
          type: types.GET_GEOCODING_FAILURE,
          meta: { address: key },
          payload,
        }
      )
    ).toEqual({
      geocoding: {
        ...geocoding,
        [key]: {
          fetching: false,
          fetched: true,
          data: null,
          error: payload,
        },
      },
    });
  });

  it('should handle invalid fething success', () => {
    const key = 'address 1';
    const payload = { status: 'ok', data: [] };
    expect(
      reducer(
        { geocoding: {} },
        {
          type: types.GET_GEOCODING_SUCCESS,
          meta: { address: key },
          payload,
        }
      )
    ).toEqual({ geocoding: {} });
  });

  it('should handle invalid fething failure', () => {
    const key = 'address 1';
    const payload = { status: 'ok', data: [] };
    expect(
      reducer(
        { geocoding: {} },
        {
          type: types.GET_GEOCODING_FAILURE,
          meta: { address: key },
          payload,
        }
      )
    ).toEqual({ geocoding: {} });
  });
});
