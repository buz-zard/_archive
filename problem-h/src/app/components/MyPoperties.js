import React from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

import properties from '../../properties';
import { WithMapsAPI } from '../../maps';
import { Loading, Property } from '../components';

class MyPoperties extends React.Component {
  componentDidMount() {
    this.props.requestData();
  }

  render() {
    const { data, hasFetched } = this.props;
    if (!hasFetched) return <Loading />;
    if (!(data && data.length)) {
      return <p>You do not have any properties to manage</p>;
    }
    return (
      <WithMapsAPI>
        {data.map(item => <Property key={item.airbnbId} {...item} />)}
      </WithMapsAPI>
    );
  }
}

MyPoperties.propTypes = {
  requestData: PropTypes.func.isRequired,
  hasFetched: PropTypes.bool.isRequired,
  data: PropTypes.arrayOf(PropTypes.shape({})),
};

MyPoperties.defaultProps = {
  data: undefined,
};

const enhance = connect(
  state => ({
    data: properties.selectors.getData(state),
    hasFetched: properties.selectors.hasFetched(state),
  }),
  dispatch => ({
    requestData: bindActionCreators(properties.actions.requestData, dispatch),
  })
);

export default enhance(MyPoperties);
