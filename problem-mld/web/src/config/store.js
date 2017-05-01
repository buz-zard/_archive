import {createStore, combineReducers, applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import {composeWithDevTools} from 'redux-devtools-extension';
import {routerReducer, routerMiddleware} from 'react-router-redux';

import * as reducers from 'src/state/reducers';
import history from './history';


const store = createStore(
  combineReducers({
    ...reducers,
    router: routerReducer,
  }),
  composeWithDevTools(applyMiddleware(thunk, routerMiddleware(history))),
);

export default store;
