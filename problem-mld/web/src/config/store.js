import {createStore, combineReducers, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import {composeWithDevTools} from 'redux-devtools-extension';

import * as reducers from 'src/state/reducers';


const store = createStore(
  combineReducers({
    ...reducers,
  }),
  composeWithDevTools(applyMiddleware(thunk)),
);

export default store;
