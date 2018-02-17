import React from 'react';
import styled from 'styled-components';

import { FAIcon } from './';

const ANIMATION_DELAY = 250;
const ANIMATION_FADE_IN = 300;
const ANIMATION_TOTAL = ANIMATION_DELAY + ANIMATION_FADE_IN;

const Container = styled.div`
  @keyframes fadeIn {
    0% {
      opacity: 0;
    }
    ${Math.floor(ANIMATION_DELAY / (ANIMATION_TOTAL / 100))}% {
      opacity: 0;
    }
    100% {
      opacity: 1;
    }
  }

  animation: fadeIn ${ANIMATION_TOTAL}ms;
`;

function Loading() {
  return (
    <Container className="pa2">
      <FAIcon type="circle-o-notch" className="fa-spin fa-2x fa-fw center db" />
    </Container>
  );
}

export default Loading;
