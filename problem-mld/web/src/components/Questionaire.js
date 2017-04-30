import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {loadQuestions, answerQuestion, submitAnswers} from 'src/state/actions/questionaire';
import Question from './question';


class Questionaire extends React.Component {

  state = {completed: false, submitting: false, submitted: false}

  componentDidMount() {
    this.props.requestQuestions();
  }

  onSaveAnswer = (answer) => {
    const {saveAnswer, lastQuestion} = this.props;
    saveAnswer(answer);
    if (lastQuestion) {
      this.setState({completed: true});
      this.onSubmit();
    }
  }

  onSubmit = () => {
    const {onSubmitAnswers} = this.props;

    if (this.state.submitted) return;
    this.setState({submitting: true});
    onSubmitAnswers()
      .then(() => this.setState({submitting: false, submitted: true}))
      .catch(() => this.setState({submitting: false}));
  }

  onReset = () => this.setState({completed: false, submitting: false, submitted: false});

  render() {
    const {loading, question, questionIndex, lastQuestion} = this.props;
    const {completed, submitting, submitted} = this.state;
    return (
      <div>
        {!completed && loading &&
          <span>Loading ...</span>
        }
        {!completed && question != null &&
          <Question number={questionIndex + 1} {...question} onSubmit={this.onSaveAnswer} last={lastQuestion} />
        }
        {completed && submitting &&
          <span>Submitting ...</span>
        }
        {completed && !submitting &&
          <button type='button' onClick={this.onSubmit}>Submit answers</button>
        }
        {completed && submitted &&
          <div>
            <p>Questionaire completed!</p>
            <button type='button' onClick={this.onReset}>Retry ?</button>
          </div>
        }
      </div>
    );
  }
}

Questionaire.propTypes = {
  requestQuestions: PropTypes.func.isRequired,
  loading: PropTypes.bool.isRequired,
  question: PropTypes.shape({}),
  questionIndex: PropTypes.number,
  saveAnswer: PropTypes.func.isRequired,
  lastQuestion: PropTypes.bool.isRequired,
  onSubmitAnswers: PropTypes.func.isRequired,
};

Questionaire.defaultProps = {
  question: null,
  questionIndex: null,
  answered: false,
  lastQuestion: false,
};


const enhance = connect(
  (state) => {
    const {currentIndex, loading, answered} = state.questionaire.questions;
    const props = {loading, answered};

    if (currentIndex != null) {
      props.question = state.questionaire.questions.data[currentIndex];
      props.questionIndex = currentIndex;
      props.lastQuestion = currentIndex === state.questionaire.questions.data.length - 1;
    }
    return props;
  },
  dispatch => ({
    requestQuestions(pageSize) {
      return dispatch(loadQuestions(pageSize));
    },
    saveAnswer(data) {
      return dispatch(answerQuestion(data));
    },
    onSubmitAnswers() {
      return dispatch(submitAnswers());
    },
  }),
);

export default enhance(Questionaire);
