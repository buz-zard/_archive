import React from 'react';
import styled from 'styled-components';

import { FAIcon } from './';

const Container = styled.footer`
  background-color: ${props => props.theme.color.bgLighGray};
`;

function Footer() {
  return (
    <Container className="ph3 pv4 ph4-ns pv5-ns ph5-l">
      <div className="tc">
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua.
      </div>
      <div className="mt2 mt3-ns f7 tc">
        <FAIcon type="copyright" /> {new Date().getFullYear()} ALL RIGHTS
        RESERVED BNBADMIN.COM
      </div>
    </Container>
  );
}

export default Footer;
