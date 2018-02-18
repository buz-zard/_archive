import React from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { Marker } from 'react-google-maps';
import { compose, setPropTypes } from 'recompose';

import maps from '../../maps';

class PropertyFocusMarker extends React.Component {
  componentDidMount() {
    this.props.requestCoordinates();
  }

  render() {
    const { coordinates } = this.props;
    if (coordinates) return <Marker position={coordinates} />;
    return null;
  }
}

PropertyFocusMarker.propTypes = {
  requestCoordinates: PropTypes.func.isRequired,
  coordinates: PropTypes.shape({
    lat: PropTypes.number.isRequired,
    lng: PropTypes.number.isRequired,
  }),
};

PropertyFocusMarker.defaultProps = {
  coordinates: undefined,
};

const enhance = compose(
  setPropTypes({
    address: PropTypes.shape({
      line1: PropTypes.string.isRequired,
      line2: PropTypes.string,
      line3: PropTypes.string,
      line4: PropTypes.string.isRequired,
      postCode: PropTypes.string.isRequired,
      city: PropTypes.string.isRequired,
      country: PropTypes.string.isRequired,
    }).isRequired,
  }),
  connect(
    (state, { address }) => ({
      coordinates: maps.selectors.getGeocodingCoordinates(
        state,
        maps.utils.makeAddressKey(address)
      ),
    }),
    (dispatch, { address }) => ({
      requestCoordinates: bindActionCreators(
        maps.actions.requestCoordinates.bind(null, address),
        dispatch
      ),
    })
  )
);

export default enhance(PropertyFocusMarker);
