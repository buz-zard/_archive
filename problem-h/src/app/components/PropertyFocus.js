import React from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import styled, { withTheme } from 'styled-components';
import { Circle } from 'react-google-maps';
import { compose } from 'recompose';

import propertiesModule from '../../properties';
import { Map } from '../../maps';
import { GMAP_STYLE } from '../style';
import { PropertyFocusMarker } from './';

const MapContainer = styled.div`
  .Map__Container {
    height: 400px;
    border-radius: 3px;
    overflow: hidden;

    .Map__Element {
      height: 100%;
    }
  }
`;

class PropertyFocus extends React.Component {
  componentDidMount() {
    this.props.requestProperties();
  }

  render() {
    const { area, zoom, theme, properties } = this.props;
    return (
      <MapContainer>
        <Map
          containerElement={<div className="Map__Container" />}
          mapElement={<div className="Map__Element" />}
          center={area.point}
          zoom={zoom}
          options={{
            mapTypeControl: false,
            streetViewControl: false,
            styles: GMAP_STYLE,
          }}
        >
          <Circle
            center={area.point}
            radius={area.radius}
            options={{
              fillColor: theme.color.orange,
              strokeOpacity: 0,
            }}
          />
          {properties &&
            properties.map(item => (
              <PropertyFocusMarker key={item.airbnbId} {...item} />
            ))}
        </Map>
      </MapContainer>
    );
  }
}

PropertyFocus.propTypes = {
  requestProperties: PropTypes.func.isRequired,
  properties: PropTypes.arrayOf(PropTypes.shape({})),
  area: PropTypes.shape({
    point: PropTypes.shape({
      lat: PropTypes.number.isRequired,
      lng: PropTypes.number.isRequired,
    }),
    radius: PropTypes.number.isRequired,
  }).isRequired,
  theme: PropTypes.shape({}).isRequired,
  zoom: PropTypes.number.isRequired,
};

PropertyFocus.defaultProps = {
  properties: undefined,
  zoom: 9.5,
};

const enhance = compose(
  withTheme,
  connect(
    state => ({
      properties: propertiesModule.selectors.getData(state),
    }),
    dispatch => ({
      requestProperties: bindActionCreators(
        propertiesModule.actions.requestData,
        dispatch
      ),
    })
  )
);

export default enhance(PropertyFocus);
