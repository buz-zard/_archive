import React from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';
import cx from 'classnames';

const Container = styled.span`
  a {
    text-decoration: none;
    color: black;

    &.active {
      font-weight: bold;
      text-decoration: underline;
    }
  }
`;

function HeaderLink({ className, ...props }) {
  return (
    <Container>
      <NavLink className={cx('Header__Link ph2', className)} {...props} />
    </Container>
  );
}

HeaderLink.propTypes = {
  className: PropTypes.string,
};

HeaderLink.defaultProps = {
  className: undefined,
};

export default HeaderLink;
