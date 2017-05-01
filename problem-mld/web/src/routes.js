import React from 'react';
import PropTypes from 'prop-types';
import {Route, Switch} from 'react-router-dom';
import {withProps} from 'recompose';

import {PublicPageContainer, PrivatePageContainer} from './components/page';
import {
  Home, Quiz,
  AdminLogin,
  NotFound,
} from './components/pages';


const AdminRoutes = ({prefix}) =>
  <PrivatePageContainer>
    <Switch>
      <Route exact path={`${prefix}/`} component={AdminLogin} />
      <Route component={NotFound} />
    </Switch>
  </PrivatePageContainer>;

AdminRoutes.propTypes = {
  prefix: PropTypes.string.isRequired,
};


const PublicRoutes = () =>
  <PublicPageContainer>
    <Switch>
      <Route exact path='/' component={Home} />
      <Route exact path='/quiz/:id' component={Quiz} />
      <Route component={NotFound} />
    </Switch>
  </PublicPageContainer>;


export default (
  <Switch>
    <Route path='/admin' component={withProps({prefix: '/admin'})(AdminRoutes)} />
    <Route path='/' component={PublicRoutes} />
  </Switch>
);
