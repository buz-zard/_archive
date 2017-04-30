import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {compose, branch, renderComponent} from 'recompose';

import LoadingIndicator from '../LoadingIndicator';
import CurrentQuestion from './CurrentQuestion';
import {QuestionShell} from '../question';


const QuestionaireSubmitted = ({onRetry}) =>
  <div>
    <p>Questionaire submitted!</p>
    <button type='button' onClick={onRetry}>Try again</button>
  </div>;

QuestionaireSubmitted.propTypes = {
  onRetry: PropTypes.func.isRequired,
};

const QuestionaireSubmit = ({onSubmit}) =>
  <div>
    <p>Oops! Something went wrong.</p>
    <button type='button' onClick={onSubmit}>Submit answers again</button>
  </div>;

QuestionaireSubmit.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};


const QuestionaireHOC = compose(
  connect(
    (state) => {
      const {completed, questions: {loading}} = state.questionaire;
      const props = {completed, loadingQuestions: loading};
      return props;
    },
  ),
  branch(props => props.loadingQuestions, renderComponent(QuestionShell)),
  branch(props => props.submitting === true, renderComponent(LoadingIndicator)),
  branch(props => props.submitted === true, renderComponent(QuestionaireSubmitted)),
  branch(props => props.completed === true, renderComponent(QuestionaireSubmit)),
)(CurrentQuestion);

export default QuestionaireHOC;
