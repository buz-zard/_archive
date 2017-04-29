import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import styled from 'styled-components';
import {compose, withProps} from 'recompose';

import cfg from '../config';
import {initializeItems, loadItems} from '../state/actions/shots';
import {InfiniteList, Cards} from './';
import {getGridSizeMetrics} from './Cards';


const Container = styled.div`
  background-color: #e8e8e8;
  height: 100%;
`;


export class App extends React.Component {

  constructor(props, context) {
    super(props, context);
    const {updateThreshold, pageSize} = props.getListParams();
    this.state = {updateThreshold};
    this.props.initializeList({pageSize});
  }

  render() {
    const {items, onItemsRequest, itemsLoading} = this.props;
    const {updateThreshold} = this.state;
    return (
      <Container>
        <InfiniteList
          items={items}
          listComponent={Cards}
          onItemsRequest={onItemsRequest}
          loading={itemsLoading}
          loadingThresholdInPX={updateThreshold}
        />
      </Container>
    );
  }
}

App.propTypes = {
  items: PropTypes.arrayOf(PropTypes.shape({})),
  getListParams: PropTypes.func.isRequired,
  initializeList: PropTypes.func.isRequired,
  onItemsRequest: PropTypes.func.isRequired,
  itemsLoading: PropTypes.bool.isRequired,
};

App.defaultProps = {
  items: null,
};


const enhance = compose(
  withProps({
    getListParams() {
      const {deviceType, rowHeight, rowCount, cardsPerRow} = getGridSizeMetrics(window.innerWidth, window.innerHeight);
      const updateThreshold = rowHeight * cfg.infinityList.updateThreshold[deviceType];
      const pageSize = Math.ceil(rowCount * cardsPerRow * cfg.infinityList.pagesToRequest[deviceType]);
      return {updateThreshold, pageSize};
    },
  }),
  connect(
    state => ({
      items: state.shots.list.data,
      itemsLoading: state.shots.list.loading,
    }),
    dispatch => ({
      initializeList(pageSize) {
        dispatch(initializeItems(pageSize));
      },
      onItemsRequest() {
        dispatch(loadItems());
      },
    }),
  ),
);


export default enhance(App);
