import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {compose, branch, renderComponent} from 'recompose';

import {Button, LoadingIndicator} from 'src/components';
import CurrentQuestion from './CurrentQuestion';
import {QuestionShell} from '../question';


const QuestionaireSubmitted = ({onRetry}) =>
  <div>
    <p>Questionaire submitted!</p>
    <Button onClick={onRetry}>Try again</Button>
  </div>;

QuestionaireSubmitted.propTypes = {
  onRetry: PropTypes.func.isRequired,
};

const QuestionaireSubmit = ({onSubmit}) =>
  <div>
    <p>Oops! Something went wrong.</p>
    <Button onClick={onSubmit}>Submit answers again</Button>
  </div>;

QuestionaireSubmit.propTypes = {
  onSubmit: PropTypes.func.isRequired,
};


const QuestionaireRenderer = compose(
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

export default QuestionaireRenderer;
