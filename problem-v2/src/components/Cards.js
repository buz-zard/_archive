import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Card from './Card';


const Container = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  width: 100%;
  margin: 1.5rem auto;

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
