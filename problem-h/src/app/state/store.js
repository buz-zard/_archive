import { createStore } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';

import reducers from './reducers';
import middlewares from './middlewares';

const store = createStore(reducers, composeWithDevTools(middlewares));

export default store;
