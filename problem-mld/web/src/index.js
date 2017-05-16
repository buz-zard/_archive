import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';
import {ThemeProvider} from 'styled-components';
import {BrowserRouter as Router} from 'react-router-dom';

import {store, history} from './config';
import routes from './routes';
import {getTheme} from './style';


render(
  <Provider store={store}>
    <ThemeProvider theme={getTheme()}>
      <Router history={history}>
        {routes}
      </Router>
    </ThemeProvider>
  </Provider>,
  document.getElementById('root'),
);
