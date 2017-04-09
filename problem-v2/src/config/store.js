import {createStore, combineReducers} from 'redux';
import {composeWithDevTools} from 'redux-devtools-extension';

import * as reducers from '../state/reducers';


const store = createStore(
  combineReducers({
    ...reducers,
  }),
  composeWithDevTools(),
);

export default store;
