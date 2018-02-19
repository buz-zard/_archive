import reducer from './reducer';
import * as types from './actionTypes';

describe('reducer', () => {
  it('should initialize on fething start', () => {
    expect(reducer({}, { type: types.GET_DATA })).toEqual({
      fetching: true,
      fetched: false,
      data: null,
      error: null,
    });
  });

  it('should handle fething success', () => {
    const payload = [{ id: 3 }, { title: 'www' }];
    expect(
      reducer(
        {
          data: null,
          error: null,
          fetching: true,
          fetched: false,
        },
        { type: types.GET_DATA_SUCCESS, payload }
      )
    ).toEqual({
      fetching: false,
      fetched: true,
      data: payload,
      error: null,
    });
  });

  it('should handle fething failure', () => {
    const payload = { some: 'arbitrary reason' };
    expect(
      reducer(
        {
          data: null,
          error: null,
          fetching: true,
          fetched: false,
        },
        { type: types.GET_DATA_FAILURE, payload }
      )
    ).toEqual({
      fetching: false,
      fetched: true,
      data: null,
      error: payload,
    });
  });
});
