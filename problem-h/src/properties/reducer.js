import Immutable from 'seamless-immutable';

import * as types from './actionTypes';

const DEFAULT_STATE = Immutable({
  data: null,
  error: null,
  fetching: false,
  fetched: false,
});

export default (state = DEFAULT_STATE, { type, payload }) => {
  switch (type) {
    case types.GET_DATA:
      return Immutable({
        ...DEFAULT_STATE,
        fetching: true,
      });
    case types.GET_DATA_SUCCESS:
      return Immutable({
        ...state,
        data: payload,
        fetching: false,
        fetched: true,
      });
    case types.GET_DATA_FAILURE:
      return Immutable({
        ...state,
        error: payload,
        fetching: false,
        fetched: true,
      });
    default:
      return state;
  }
};
