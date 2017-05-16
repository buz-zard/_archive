import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Header from './Header';


const Container = styled.div`
  height: 100%;
`;

const Content = styled.div`
  width: 70%;
`;


const PublicPageContainer = ({children}) =>
  <Container>
    <Header />
    <Content className='center'>
      {children}
    </Content>
  </Container>;

PublicPageContainer.propTypes = {
  children: PropTypes.node,
};

PublicPageContainer.defaultProps = {
  children: null,
};

export default PublicPageContainer;
