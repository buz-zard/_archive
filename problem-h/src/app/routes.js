import React from 'react';
import { Route, Switch } from 'react-router-dom';

import { Home, Properties } from './pages';

const routes = () => (
  <Switch>
    <Route exact path="/" component={Home} />
    <Route exact path="/properties" component={Properties} />
    <Route render={() => <div>404</div>} />
  </Switch>
);

export default routes;
