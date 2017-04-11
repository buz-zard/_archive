import React from 'react';
import PropTypes from 'prop-types';
import {componentFromProp, renderNothing, branch} from 'recompose';
import styled from 'styled-components';

import {Icon} from './';


const Container = styled.div`
  height: 100%;
  overflow-y: auto;
  padding: 1rem 0;
`;

const Spinner = styled.div`
  display: flex;
  justify-content: center;
  margin: 1rem;
`;


const ItemList = branch(
  props => !(props.items && props.items.length > 0),
  renderNothing,
)(componentFromProp('component'));


class InfiniteList extends React.Component {

  componentDidMount() {
    this.refContainer.addEventListener('scroll', this.loadIfNeeded);
    window.addEventListener('resize', this.loadIfNeeded);
    this.loadIfNeeded();
  }

  componentDidUpdate() {
    this.loadIfNeeded();
  }

  componentWillUnmount() {
    this.refContainer.removeEventListener('scroll', this.loadIfNeeded);
    window.removeEventListener('resize', this.loadIfNeeded);
  }

  loadIfNeeded = () => {
    const {loading, items, loadingThresholdInPX, onItemsRequest} = this.props;
    if (loading) return;
    if (!(items && items.length)) {
      onItemsRequest();
    } else {
      const container = this.refContainer.getBoundingClientRect();
      const bottom = this.refBottom.getBoundingClientRect();
      if (bottom.bottom - container.bottom < loadingThresholdInPX) {
        onItemsRequest();
      }
    }
  }

  render() {
    const {items, listComponent, loading} = this.props;
    return (
      <Container innerRef={(ref) => { this.refContainer = ref; }} >
        <ItemList items={items} component={listComponent} />
        <div ref={(ref) => { this.refBottom = ref; }} />
        {loading &&
          <Spinner>
            <Icon className='fa-refresh fa-spin' />
          </Spinner>
        }
      </Container>
    );
  }
}

InfiniteList.propTypes = {
  items: PropTypes.arrayOf(PropTypes.shape({})),
  listComponent: PropTypes.func.isRequired,
  onItemsRequest: PropTypes.func.isRequired,
  loadingThresholdInPX: PropTypes.number.isRequired,
  loading: PropTypes.bool.isRequired,
};

InfiniteList.defaultProps = {
  items: null,
  loadingThresholdInPX: 600,
};

export default InfiniteList;
