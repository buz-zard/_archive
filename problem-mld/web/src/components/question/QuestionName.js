import React from 'react';
import PropTypes from 'prop-types';


const QuestionName = ({name, number}) =>
  <div className='mb2'><b>{number}.</b> {name}</div>;

QuestionName.propTypes = {
  number: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
};

export default QuestionName;
