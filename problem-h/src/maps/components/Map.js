import React from 'react';
import { withGoogleMap, GoogleMap } from 'react-google-maps';

function Map(props) {
  return <GoogleMap {...props} />;
}

const enhance = withGoogleMap;

export default enhance(Map);
