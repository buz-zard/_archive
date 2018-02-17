import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';

import { theme } from './style';
import routes from './routes';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Router>{routes()}</Router>
    </ThemeProvider>
  );
}

export default App;
