import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import routes from './routes';
import './style';

function App() {
  return <Router>{routes()}</Router>;
}

export default App;
