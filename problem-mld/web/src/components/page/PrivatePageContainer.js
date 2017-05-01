import React from 'react';
import PropTypes from 'prop-types';


const PrivatePageContainer = ({children}) =>
  <div>
    PrivatePageContainer
    {children}
  </div>;

PrivatePageContainer.propTypes = {
  children: PropTypes.node,
};

PrivatePageContainer.defaultProps = {
  children: null,
};

export default PrivatePageContainer;
