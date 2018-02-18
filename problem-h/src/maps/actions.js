import { RSAA } from 'redux-api-middleware';

import { API_KEY } from './constants';
import * as types from './actionTypes';
import * as selectors from './selectors';
import { makeAddressKey } from './utils';

// async
export const requestCoordinates = addressData => async (dispatch, getState) => {
  const state = getState();
  const address = makeAddressKey(addressData);
  if (!address) return false;
  if (selectors.isGeocodingFetching(state, address)) return false;
  if (
    selectors.hasGeocodingFetched(state, address) &&
    selectors.getGeocodingError(state, address) == null
  ) {
    return false;
  }
  const meta = { address };

  const result = await dispatch({
    [RSAA]: {
      endpoint: `https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(
        address
      )}&key=${API_KEY}`,
      method: 'GET',
      types: [
        { type: types.GET_GEOCODING, meta },
        { type: types.GET_GEOCODING_SUCCESS, meta },
        { type: types.GET_GEOCODING_FAILURE, meta },
      ],
    },
  });
  return result;
};
