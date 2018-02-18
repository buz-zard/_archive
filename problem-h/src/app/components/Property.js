import React from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import styled from 'styled-components';
import { Marker } from 'react-google-maps';
import haversine from 'haversine';

import maps, { Map } from '../../maps';
import { FOCUS_AREA } from '../constants';
import { toNonRoundedFixedString } from '../utils';
import { GMAP_STYLE } from '../style';
import { FAIcon } from './';

const DEFAULT_COORDINATES = { lat: 51.507, lng: 0.127 };

const MapContainer = styled.div`
  .Map__Container {
    height: 200px;
    border-radius: 3px;
    overflow: hidden;

    .Map__Element {
      height: 100%;
    }
  }
`;

class Property extends React.Component {
  componentDidMount() {
    this.props.requestCoordinates();
  }

  render() {
    const {
      owner,
      address: { line1, line2, line3, line4, postCode, city, country },
      incomeGenerated,
      coordinates,
    } = this.props;
    return (
      <section className="cf mt3 mb5">
        <div className="fl w-100 mb3 w-50-l">
          <div className="fl w-100 b pb2">
            <div className="fl w-25">Owner</div>
            <div className="fl w-50">Address</div>
            <div className="fl w-25">Generated Income</div>
          </div>
          <div className="fl w-100">
            <div className="fl w-25">{owner}</div>
            <div className="fl w-50">
              {[line1, line2, line3, line4, postCode, city, country]
                .filter(Boolean)
                .map(item => <div key={item}>{item}</div>)}
            </div>
            <div className="fl w-25">
              {toNonRoundedFixedString(incomeGenerated, 2)} £
            </div>
          </div>
          {coordinates && (
            <div className="fl w-100 mt3">
              {haversine(
                { latitude: coordinates.lat, longitude: coordinates.lng },
                {
                  latitude: FOCUS_AREA.point.lat,
                  longitude: FOCUS_AREA.point.lng,
                },
                { unit: 'meter' }
              ) > FOCUS_AREA.radius ? (
                <div className="c-gray">
                  <FAIcon type="exclamation-circle" /> low focus
                </div>
              ) : (
                <div className="c-orange">
                  <FAIcon type="trophy" /> high focus
                </div>
              )}
            </div>
          )}
        </div>
        <MapContainer className="fl w-100 w-50-l">
          <Map
            containerElement={<div className="Map__Container" />}
            mapElement={<div className="Map__Element" />}
            defaultZoom={12}
            defaultCenter={DEFAULT_COORDINATES}
            options={{
              mapTypeControl: false,
              streetViewControl: false,
              styles: GMAP_STYLE,
            }}
            isMarkerShown={!!coordinates}
            center={coordinates}
          >
            {coordinates && <Marker position={coordinates} />}
          </Map>
        </MapContainer>
      </section>
    );
  }
}

Property.propTypes = {
  owner: PropTypes.string.isRequired,
  address: PropTypes.shape({
    line1: PropTypes.string.isRequired,
    line2: PropTypes.string,
    line3: PropTypes.string,
    line4: PropTypes.string.isRequired,
    postCode: PropTypes.string.isRequired,
    city: PropTypes.string.isRequired,
    country: PropTypes.string.isRequired,
  }).isRequired,
  incomeGenerated: PropTypes.number.isRequired,
  requestCoordinates: PropTypes.func.isRequired,
  coordinates: PropTypes.shape({
    lat: PropTypes.number.isRequired,
    lng: PropTypes.number.isRequired,
  }),
};

Property.defaultProps = {
  coordinates: undefined,
};

const enhance = connect(
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
);

export default enhance(Property);
