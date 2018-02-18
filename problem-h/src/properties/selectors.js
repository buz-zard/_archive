import idx from 'idx';

import { NAME } from './constants';

export const isFetching = state => idx(state, _ => _[NAME].fetching);
export const hasFetched = state => idx(state, _ => _[NAME].fetched);
export const getData = state => idx(state, _ => _[NAME].data);
export const getError = state => idx(state, _ => _[NAME].error);
