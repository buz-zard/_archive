import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import cfg from '../config';
import Card, {dimensions} from './Card';


export const getGridSizeMetrics = (width, height) => {
  const rowHeight = dimensions.height + 20;
  const rowCount = Math.ceil(height / (dimensions.height + 20));
  let cardsPerRow;
  let deviceType;
  if (width >= cfg.breakpoints.desktop) { // desktop
    cardsPerRow = Math.floor((width * 0.7) / (dimensions.width + 20));
    deviceType = 'desktop';
  } else if (width >= cfg.breakpoints.tablet) { // tablet
    cardsPerRow = Math.floor((width * 0.85) / (dimensions.width + 20));
    deviceType = 'tablet';
  } else { // phone
    cardsPerRow = Math.floor(width / (dimensions.width + 20));
    deviceType = 'phone';
  }
  if (cardsPerRow < 1) cardsPerRow = 1;
  return {deviceType, rowHeight, rowCount, cardsPerRow, cardCount: rowCount * cardsPerRow};
};


const Container = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  margin: 0 auto;

  @media(${props => props.theme.breakpoints.tablet}) {
    width: 85%;
  }

  @media(${props => props.theme.breakpoints.desktop}) {
    width: 70%;
  }
`;


const Cards = ({items}) =>
  <Container>
    {items.map(item =>
      <Card key={item.id} {...item} />,
    )}
  </Container>;

Cards.propTypes = {
  items: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
  })).isRequired,
};

export default Cards;
