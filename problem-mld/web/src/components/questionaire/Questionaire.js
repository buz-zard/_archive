import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {loadQuestions, completeQuestionaire, submitAnswers} from 'src/state/actions/questionaire';
import QuestionaireHOC from './QuestionaireHOC';


class Questionaire extends React.Component {

  state = {submitting: false, submitted: false}

  componentDidMount() {
    this.props.requestQuestions();
  }

  onSubmit = () => {
    const {onSubmitAnswers} = this.props;

    if (this.state.submitted) return;

    this.setState({submitting: true});
    onSubmitAnswers()
      .then(() => this.setState({submitting: false, submitted: true}))
      .catch(() => this.setState({submitting: false}));
  }

  onRetry = () => this.setState({submitting: false, submitted: false});

  render() {
    const {submitting, submitted} = this.state;
    return (
      <QuestionaireHOC
        submitting={submitting}
        submitted={submitted}
        onSubmit={this.onSubmit}
        onRetry={this.onRetry}
      />
    );
  }
}

Questionaire.propTypes = {
  requestQuestions: PropTypes.func.isRequired,
  onSubmitAnswers: PropTypes.func.isRequired,
};


const enhance = connect(
  null,
  dispatch => ({
    requestQuestions(pageSize) {
      return dispatch(loadQuestions(pageSize));
    },
    onSubmitAnswers() {
      dispatch(completeQuestionaire());
      return dispatch(submitAnswers());
    },
  }),
);

export default enhance(Questionaire);
