import update from 'immutability-helper';

import {
  ADDED_TO_FAVOURITES,
  REMOVED_FROM_FAVOURITES,
} from '../actions/shots';


const DEFAULT_STATE = {
  favourites: [],
};


export default (state = DEFAULT_STATE, {type, payload}) => {
  switch (type) {
    case ADDED_TO_FAVOURITES:
      if (state.favourites.indexOf(payload) === -1) {
        return update(state, {
          favourites: {$push: [payload]},
        });
      }
      return state;
    case REMOVED_FROM_FAVOURITES: {
      if (state.favourites.indexOf(payload) >= 0) {
        return update(state, {
          favourites: {$set: state.favourites.filter(id => id !== payload)},
        });
      }
      return state;
    }
    default:
      return state;
  }
};
