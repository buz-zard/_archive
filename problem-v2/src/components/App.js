import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import styled from 'styled-components';

import {initializeItems, loadItems} from '../state/actions/shots';
import {InfiniteList, Cards} from './';


const Container = styled.div`
  background-color: #e8e8e8;
  height: 100%;
`;


class App extends React.Component {

  componentWillMount() {
    this.props.initializeList({pageSize: 30});
  }

  render() {
    const {items, onItemsRequest, itemsLoading} = this.props;
    return (
      <Container>
        <InfiniteList items={items} listComponent={Cards} onItemsRequest={onItemsRequest} loading={itemsLoading} />
      </Container>
    );
  }
}

App.propTypes = {
  items: PropTypes.arrayOf(PropTypes.shape({})),
  initializeList: PropTypes.func.isRequired,
  onItemsRequest: PropTypes.func.isRequired,
  itemsLoading: PropTypes.bool.isRequired,
};

App.defaultProps = {
  items: null,
};


const enhance = connect(
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
);

export default enhance(App);
