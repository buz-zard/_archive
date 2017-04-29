import React from 'react';
import styled from 'styled-components';


import Questionaire from './Questionaire';


const Container = styled.div`
  width: 70%;
`;


const App = () =>
  <Container className='center mv5'>
    <Questionaire />
  </Container>;

export default App;
