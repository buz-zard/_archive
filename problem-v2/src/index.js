import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';
import {ThemeProvider} from 'styled-components';

import {init, store} from './config';
import {App} from './components';
import {getTheme} from './styles';


init();


render(
  <Provider store={store}>
    <ThemeProvider theme={getTheme()}>
      <App />
    </ThemeProvider>
  </Provider>,
  document.getElementById('root'),
);
