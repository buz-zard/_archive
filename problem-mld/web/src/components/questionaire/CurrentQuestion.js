import React from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {compose, branch, renderComponent, renderNothing} from 'recompose';

import {answerQuestion} from 'src/state/actions/questionaire';
import Question from '../question';


const CurrentQuestion = ({question, questionIndex, saveAnswer, lastQuestion, onSubmit}) =>
  <Question
    {...question}
    number={questionIndex + 1}
    last={lastQuestion}
    onSubmit={(answer) => {
      saveAnswer(answer);
      if (lastQuestion) onSubmit();
    }}
  />;

CurrentQuestion.propTypes = {
  question: PropTypes.shape({}).isRequired,
  questionIndex: PropTypes.number.isRequired,
  saveAnswer: PropTypes.func.isRequired,
  lastQuestion: PropTypes.bool.isRequired,
  onSubmit: PropTypes.func.isRequired,
};


const CurrentQuestionHOC = compose(
  connect(
    (state) => {
      const {completed, questions: {currentIndex, data: questions}} = state.questionaire;
      const props = {completed};

      if (currentIndex != null) {
        props.question = questions[currentIndex];
        props.questionIndex = currentIndex;
        props.lastQuestion = currentIndex === questions.length - 1;
      }
      return props;
    },
    dispatch => ({
      saveAnswer(data) {
        return dispatch(answerQuestion(data));
      },
    }),
  ),
  branch(props => props.question != null && props.completed !== true, renderComponent(CurrentQuestion)),
)(renderNothing());

export default CurrentQuestionHOC;
