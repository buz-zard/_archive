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
    this.props.onItemsRequest();
    this.refContainer.addEventListener('scroll', this.onScroll);
  }

  componentWillUnmount() {
    this.refContainer.removeEventListener('scroll', this.onScroll);
  }

  onScroll = () => {
    if (this.shouldLoad()) {
      this.props.onItemsRequest();
    }
  }

  shouldLoad = () => {
    const container = this.refContainer.getBoundingClientRect();
    const bottom = this.refBottom.getBoundingClientRect();
    return bottom.bottom - container.bottom < this.props.loadingThresholdInPX;
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
