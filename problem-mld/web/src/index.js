import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';

import {store} from './config';
import {App} from './components';


render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root'),
);
