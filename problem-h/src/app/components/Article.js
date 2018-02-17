import React from 'react';
import PropTypes from 'prop-types';

function Article({ title, children }) {
  return (
    <section className="mt3 mb4 tj">
      <h1>{title}</h1>
      {children}
    </section>
  );
}

Article.propTypes = {
  title: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
};

export default Article;
