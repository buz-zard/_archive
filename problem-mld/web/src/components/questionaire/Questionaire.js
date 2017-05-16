import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {compose, withProps} from 'recompose';

import {
  initialize, loadMetadata, loadQuestions,
  completeQuestionaire, submitAnswers,
} from 'src/state/actions/questionaire';
import QuestionaireRenderer from './QuestionaireRenderer';


class Questionaire extends React.Component {

  state = {submitting: false, submitted: false, result: null}

  componentDidMount() {
    const {requestQuestions, id} = this.props;
    requestQuestions(id);
  }

  onSubmit = () => {
    const {onSubmitAnswers} = this.props;

    if (this.state.submitted) return;

    this.setState({submitting: true});
    onSubmitAnswers()
      .then(data => this.setState({submitting: false, submitted: true, result: data}))
      .catch(() => this.setState({submitting: false}));
  }

  onRetry = () => this.setState({submitting: false, submitted: false});

  render() {
    const {info} = this.props;
    const {submitting, submitted, result} = this.state;
    return (
      <div>
        <h3>
          {info && info.name}&nbsp;
        </h3>
        <QuestionaireRenderer
          submitting={submitting}
          submitted={submitted}
          onSubmit={this.onSubmit}
          onRetry={this.onRetry}
          result={result}
        />
      </div>
    );
  }
}

Questionaire.propTypes = {
  id: PropTypes.number.isRequired,
  info: PropTypes.shape({
    name: PropTypes.string.isRequired,
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
        return new Promise((resolve, reject) => {
          Promise.all([
            dispatch(completeQuestionaire()),
            dispatch(submitAnswers()),
          ]).then(data => resolve(data[1])).catch(reject);
        });
      },
    }),
  ),
);

export default enhance(Questionaire);
