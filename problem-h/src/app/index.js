import React from 'react';
import { ThemeProvider } from 'styled-components';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';

import { WithMapsAPI } from '../maps';
import { theme } from './style';
import { store } from './state';
import routes from './routes';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Provider store={store}>
        <WithMapsAPI>
          <Router>{routes()}</Router>
        </WithMapsAPI>
      </Provider>
    </ThemeProvider>
  );
}

export default App;
