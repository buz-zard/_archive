import React from 'react';
import styled, {keyframes} from 'styled-components';


const loading = keyframes`
  0% {
    opacity: .5;
  }
  25% {
    opacity: .15;
  }
  50% {
    opacity: 1;
  }
  75% {
    opacity: .15;
  }
  100% {
    opacity: .5;
  }
`;


const Container = styled.div`
  font-family: 'BLOKK', serif;
  color: #C7C6C6;
  animation: ${loading} 3s infinite cubic-bezier(.65, .05, .36, 1);
`;


const QuestionShell = () =>
  <Container>
    <div className='mb2'>1. Lorem ipsum dolor sit amet, consectetur adipiscing?</div>
    <div>
      <div className='mv1'>
        ipsum dolor sit, consectetur.
      </div>
      <div className='mv1'>
        Lorem ipsum dolor.
      </div>
      <div className='mv1'>
        Consectetur adipiscing elit.
      </div>
      <div className='mv1'>
        Lorem.
      </div>
    </div>
    <div>
      Next
    </div>
  </Container>;

export default QuestionShell;
