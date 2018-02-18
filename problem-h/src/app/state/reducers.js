import { combineReducers } from 'redux';

import maps from '../../maps';

const reducers = combineReducers({
  [maps.constants.NAME]: maps.reducer,
});

export default reducers;
