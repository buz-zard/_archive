import React from 'react';
import {Link} from 'react-router-dom';
import styled from 'styled-components';

import Icon from 'src/components/Icon';


const Container = styled.div`
  color: ${props => props.theme.color.accent};
  display: flex;
  align-items: center;

  > *:last-child {
    color: ${props => props.theme.color.accent};
    text-decoration: none;
    letter-spacing: .5rem;
    font-weight: 700;
  }
`;


const Header = () =>
  <Container className='pa4'>
    <Icon className='fa-question-circle-o fa-2x mr3' />
    <Link to='/'>QUIZY</Link>
  </Container>;

export default Header;
