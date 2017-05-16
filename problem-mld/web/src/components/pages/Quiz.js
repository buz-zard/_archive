import React from 'react';
import PropTypes from 'prop-types';
import _ from 'lodash';

import Questionaire from '../questionaire';


const Quiz = ({match}) =>
  <div className='mv4'>
    <Questionaire id={_.toInteger(match.params.id)} />
  </div>;

Quiz.propTypes = {
  match: PropTypes.shape({
    params: PropTypes.shape({
      id: PropTypes.string.isRequired,
    }).isRequired,
  }).isRequired,
};

export default Quiz;
