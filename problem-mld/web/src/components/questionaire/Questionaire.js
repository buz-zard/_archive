import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {compose, withProps} from 'recompose';

import {
  initialize, loadMetadata, loadQuestions,
  completeQuestionaire, submitAnswers,
} from 'src/state/actions/questionaire';
import QuestionaireHOC from './QuestionaireHOC';


class Questionaire extends React.Component {

  state = {submitting: false, submitted: false}

  componentDidMount() {
    const {requestQuestions, id} = this.props;
    requestQuestions(id);
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
    const {info} = this.props;
    const {submitting, submitted} = this.state;
    return (
      <div>
        <h3>
          {info && info.label}&nbsp;
        </h3>
        <QuestionaireHOC
          submitting={submitting}
          submitted={submitted}
          onSubmit={this.onSubmit}
          onRetry={this.onRetry}
        />
      </div>
    );
  }
}

Questionaire.propTypes = {
  id: PropTypes.number.isRequired,
  info: PropTypes.shape({
    label: PropTypes.string.isRequired,
  }),
  requestQuestions: PropTypes.func.isRequired,
  onSubmitAnswers: PropTypes.func.isRequired,
};

Questionaire.defaultProps = {
  info: null,
};


const enhance = compose(
  withProps({}),
  connect(
    state => ({info: state.questionaire.info}),
    dispatch => ({
      requestQuestions(questionaireId) {
        return Promise.all([
          dispatch(initialize(questionaireId)),
          dispatch(loadMetadata()),
          dispatch(loadQuestions()),
        ]);
      },
      onSubmitAnswers() {
        return Promise.all([
          dispatch(completeQuestionaire()),
          dispatch(submitAnswers()),
        ]);
      },
    }),
  ),
);

export default enhance(Questionaire);
