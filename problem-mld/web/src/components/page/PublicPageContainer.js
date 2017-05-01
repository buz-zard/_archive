import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';


const Container = styled.div`
  width: 70%;
`;


const PublicPageContainer = ({children}) =>
  <Container className='center'>
    {children}
  </Container>;

PublicPageContainer.propTypes = {
  children: PropTypes.node,
};

PublicPageContainer.defaultProps = {
  children: null,
};

export default PublicPageContainer;
