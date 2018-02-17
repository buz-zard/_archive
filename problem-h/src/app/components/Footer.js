import React from 'react';
import styled from 'styled-components';

import { FAIcon } from './';

const Container = styled.footer`
  background-color: #efefef;
`;

function Footer() {
  return (
    <Container className="ph3 pv4 ph4-ns pv5-ns ph5-l">
      <div>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua.
      </div>
      <div className="mt2 mt3-ns f7">
        <FAIcon type="copyright" /> {new Date().getFullYear()} ALL RIGHTS
        RESERVED THECOMPANY.COM
      </div>
    </Container>
  );
}

export default Footer;
