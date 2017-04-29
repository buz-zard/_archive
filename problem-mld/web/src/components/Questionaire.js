import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';

import {loadQuestions} from 'src/state/actions/questionaire';
import Question from './question';


class Questionaire extends React.Component {

  componentDidMount() {
    this.props.requestQuestions();
  }

  render() {
    const {loading, question, questionIndex} = this.props;
    return (
      <div>
        {loading &&
          <span>Loading</span>
        }
        {question != null &&
          <Question index={questionIndex} {...question} />
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
};

Questionaire.defaultProps = {
  question: null,
  questionIndex: null,
};


const enhance = connect(
  (state) => {
    const props = {
      loading: state.questionaire.questions.loading,
    };
    if (state.questionaire.questions.data != null && state.questionaire.questions.data.length) {
      props.question = state.questionaire.questions.data[0];
      props.questionIndex = 1;
    }
    return props;
  },
  dispatch => ({
    requestQuestions(pageSize) {
      dispatch(loadQuestions(pageSize));
    },
  }),
);

export default enhance(Questionaire);
