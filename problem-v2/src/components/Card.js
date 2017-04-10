import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import styled from 'styled-components';
import LazyLoad from 'react-lazy-load';

import {addToFavourites, removeFromFavourites} from '../state/actions/shots';
import Icon from './Icon';


export const dimensions = {
  width: 200,
  height: 150,
};


const Container = styled.div`
  width: ${dimensions.width}px;
  height: ${dimensions.height}px;
  border-radius: 4px;
  box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.25);
  margin: .5rem;
  position: relative;

  &:hover {
    box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.25);

    &:before {
      content: '';
      position: absolute;
      width: inherit;
      height: inherit;
      border-radius: inherit;
      background-color: rgba(256, 256, 256, .2);
    }
  }

  .LazyLoad {
    border-radius: inherit;
  }

  img {
    border-radius: inherit;
    flex-basis: 1rem;
  }
`;

const CardIcon = styled(Icon)`
  position: absolute;
  padding: 10px;
  cursor: pointer;
  mix-blend-mode: exclusion;
`;

const StarIcon = styled(CardIcon)`
  right: 0;
  color: #f1c40f;
`;

const LinkIcon = styled(CardIcon)`
  bottom: 0;
  color: #2980b9;
`;


const Card = ({image, title, url, isFavourited, handleFavourite, handleUnFavourite}) =>
  <Container>
    <StarIcon
      className={isFavourited ? 'fa-star' : 'fa-star-o'}
      onClick={isFavourited ? handleUnFavourite : handleFavourite}
    />
    <LinkIcon className='fa-link' onClick={() => window.open(url, '_blank')} />
    <LazyLoad threshold={dimensions.height * 1.5}>
      <img src={image} alt={title} />
    </LazyLoad>
  </Container>;

Card.propTypes = {
  id: PropTypes.number.isRequired, // eslint-disable-line
  image: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
  isFavourited: PropTypes.bool.isRequired,
  handleFavourite: PropTypes.func.isRequired,
  handleUnFavourite: PropTypes.func.isRequired,
};

const enhance = connect(
  (state, {id}) => ({
    isFavourited: state.shots.favourites.indexOf(id) >= 0,
  }),
  (dispatch, {id}) => ({
    handleFavourite() {
      dispatch(addToFavourites(id));
    },
    handleUnFavourite() {
      dispatch(removeFromFavourites(id));
    },
  }),
);

export default enhance(Card);
