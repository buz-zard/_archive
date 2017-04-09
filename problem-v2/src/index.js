import React from 'react';
import {render} from 'react-dom';
import {ThemeProvider} from 'styled-components';

import {init} from './config';
import {App} from './components';
import {theme} from './styles';


init();


render(
  <ThemeProvider theme={theme}>
    <App />
  </ThemeProvider>,
  document.getElementById('root'),
);
