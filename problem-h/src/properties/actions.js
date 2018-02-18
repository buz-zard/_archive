import * as types from './actionTypes';
import * as selectors from './selectors';
import { getProperties } from './utils';

// async
export const requestData = () => async (dispatch, getState) => {
  const state = getState();
  if (selectors.isFetching(state)) return false;
  if (selectors.hasFetched(state) && selectors.getError(state) == null) {
    return false;
  }

  dispatch({ type: types.GET_DATA });
  const result = await getProperties();
  const action = { type: types.GET_DATA_SUCCESS, payload: result };
  dispatch(action);
  return action;
};
