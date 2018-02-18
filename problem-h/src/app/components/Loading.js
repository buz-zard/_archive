import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';
import cx from 'classnames';

import { FAIcon } from './';

const ANIMATION_DELAY = 200;
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
  color: ${props => props.theme.color.gray};
`;

function Loading({ className }) {
  return (
    <Container className={cx('pa3', className)}>
      <FAIcon type="circle-o-notch" className="fa-spin fa-2x fa-fw center db" />
    </Container>
  );
}

Loading.propTypes = {
  className: PropTypes.string.isRequired,
};

Loading.defaultProps = {
  className: undefined,
};

export default Loading;
