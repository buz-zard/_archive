import React from 'react';
import PropTypes from 'prop-types';

import { Header, Footer } from './';

function Page({ children }) {
  return (
    <React.Fragment>
      <Header />
      <main>{children}</main>
      <Footer />
    </React.Fragment>
  );
}

Page.propTypes = {
  children: PropTypes.node.isRequired,
};

export default Page;
