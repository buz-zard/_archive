import update from 'immutability-helper';

import {
  ITEMS_INITIALIZED,
  ITEMS_LOADING_STARTED,
  ITEMS_LOADING_FINISHED,
  ADDED_TO_FAVOURITES,
  REMOVED_FROM_FAVOURITES,
} from '../actions/shots';


const DEFAULT_STATE = {
  favourites: [],
  list: {
    loading: false,
    page: 0,
    pageSize: null,
    data: [],
  },
};


export default (state = DEFAULT_STATE, {type, payload}) => {
  switch (type) {
    case ITEMS_INITIALIZED:
      return update(state, {
        list: {$set: {
          ...DEFAULT_STATE.list,
          pageSize: payload.pageSize,
        }},
      });
    case ITEMS_LOADING_STARTED:
      if (payload.page === state.list.page + 1) {
        return update(state, {
          list: {$merge: {
            loading: true,
            page: payload.page,
          }},
        });
      }
      return state;
    case ITEMS_LOADING_FINISHED:
      if (payload.page === state.list.page) {
        return update(state, {
          list: {$merge: {
            loading: false,
            data: state.list.data.concat(payload.data
              .filter(item => !state.list.data.find(loadedItem => loadedItem.id === item.id)),
            ),
          }},
        });
      }
      return state;
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
