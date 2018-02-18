import { combineReducers } from 'redux';

import properties from '../../properties';
import maps from '../../maps';

const reducers = combineReducers({
  [properties.constants.NAME]: properties.reducer,
  [maps.constants.NAME]: maps.reducer,
});

export default reducers;
