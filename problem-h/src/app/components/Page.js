import React from 'react';
import PropTypes from 'prop-types';

import { Header, Footer } from './';

function Page({ children, className }) {
  return (
    <React.Fragment>
      <Header />
      <main className={className}>{children}</main>
      <Footer />
    </React.Fragment>
  );
}

Page.propTypes = {
  children: PropTypes.node.isRequired,
  className: PropTypes.string,
};

Page.defaultProps = {
  className: undefined,
};

export default Page;
