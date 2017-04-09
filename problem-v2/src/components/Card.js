import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';


const Container = styled.div`
  width: 200px;
  height: 150px;
  margin: .5rem;
  position: relative;

  a {
    width: inherit;
    height: inherit;
    border-radius: 4px;
    box-shadow: 0px 1px 2px 0px rgba(0, 0, 0, 0.75);
    position: absolute;
  }

  a:hover, a:active, a:focus {
    outline: 0;
    box-shadow: 0px 2px 3px 0px rgba(0, 0, 0, 0.75);

    &:before {
      content: '';
      position: absolute;
      width: inherit;
      height: inherit;
      border-radius: inherit;
      background-color: rgba(256, 256, 256, .2);
      cursor: pointer;
    }
  }

  img {
    border-radius: inherit;
    flex-basis: 1rem;
  }
`;


const Card = ({image, title, url}) =>
  <Container>
    <a onClick={() => window.open(url, '_blank')} tabIndex='0'>
      <img src={image} alt={title} />
    </a>
  </Container>;

Card.propTypes = {
  image: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  url: PropTypes.string.isRequired,
};

export default Card;
