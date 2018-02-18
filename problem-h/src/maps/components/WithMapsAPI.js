import React from 'react';
import PropTypes from 'prop-types';
import { compose, withProps } from 'recompose';
import { withScriptjs } from 'react-google-maps';

import { MAP_API_URL } from '../constants';

function WithMapsAPI({ children }) {
  return children;
}

WithMapsAPI.propTypes = {
  children: PropTypes.node.isRequired,
};

const enhance = compose(
  withProps({
    googleMapURL: MAP_API_URL,
    loadingElement: <div />,
  }),
  withScriptjs
);

export default enhance(WithMapsAPI);
