import idx from 'idx';

import { NAME } from './constants';
import { DEFAULT_GEOCODING_ITEM_STATE } from './reducer';

const geocodingKeyValue = (state, key, valueName) => {
  const value = idx(state, _ => _[NAME].geocoding[key][valueName]);
  return typeof value === 'undefined'
    ? DEFAULT_GEOCODING_ITEM_STATE[valueName]
    : value;
};

export const isGeocodingFetching = (state, key) =>
  geocodingKeyValue(state, key, 'fetching');

export const hasGeocodingFetched = (state, key) =>
  geocodingKeyValue(state, key, 'fetched');

export const getGeocodingData = (state, key) =>
  geocodingKeyValue(state, key, 'data');

export const getGeocodingError = (state, key) =>
  geocodingKeyValue(state, key, 'error');

export const getGeocodingCoordinates = (state, key) => {
  const data = geocodingKeyValue(state, key, 'data');
  return idx(data, _ => _.results[0].geometry.location);
};
