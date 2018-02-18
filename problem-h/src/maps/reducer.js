import Immutable from 'seamless-immutable';

import * as types from './actionTypes';

export const DEFAULT_GEOCODING_ITEM_STATE = Immutable({
  data: null,
  error: null,
  fetching: false,
  fetched: false,
});

const DEFAULT_STATE = Immutable({
  geocoding: {},
});

export default (state = DEFAULT_STATE, { type, payload, meta }) => {
  switch (type) {
    case types.GET_GEOCODING:
      return Immutable.set(state, 'geocoding', {
        ...state.geocoding,
        [meta.address]: {
          ...DEFAULT_GEOCODING_ITEM_STATE,
          fetching: true,
        },
      });
    case types.GET_GEOCODING_SUCCESS:
      if (state.geocoding[meta.address]) {
        return Immutable.set(state, 'geocoding', {
          ...state.geocoding,
          [meta.address]: {
            ...state.geocoding[meta.address],
            data: payload,
            fetching: false,
            fetched: true,
          },
        });
      }
      return state;
    case types.GET_GEOCODING_FAILURE:
      if (state.geocoding[meta.address]) {
        return Immutable.set(state, 'geocoding', {
          ...state.geocoding,
          [meta.address]: {
            ...state.geocoding[meta.address],
            error: payload,
            fetching: false,
            fetched: true,
          },
        });
      }
      return state;
    default:
      return state;
  }
};
