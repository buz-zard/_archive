import React from 'react';
import PropTypes from 'prop-types';
import {mapProps} from 'recompose';
import styled, {keyframes} from 'styled-components';

import api from 'src/api';
import SingleQuestion from './SingleQuestion';


const showUp = keyframes`
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
`;


export const Container = styled.div`
  animation: ${showUp} .25s linear;
`;


class Question extends React.Component {

  state = {loading: true, choices: null}

  componentDidMount() {
    this.loadChoices();
  }

  componentDidUpdate(prevProps) {
    if (this.props.id !== prevProps.id) {
      this.loadChoices(true);
    }
  }

  loadChoices = (skipInitial = false) => {
    if (!skipInitial) {
      this.setState({loading: true, choices: null});
    }
    this.props.loadChoices()
      .then(data => this.setState({choices: data, loading: false}))
      .catch(() => this.setState({loading: false}));
  }

  render() {
    const {component: Component} = this.props;
    const {choices, loading} = this.state;
    return <Component {...this.props} choices={choices} loading={loading} />;
  }
}

Question.propTypes = {
  id: PropTypes.number.isRequired,
  loadChoices: PropTypes.func.isRequired,
  component: PropTypes.func.isRequired,
};


const enhance = mapProps(({id, type, ...props}) => {
  const newProps = {
    id,
    ...props,
    loadChoices() {
      return api.getChoices(id);
    },
  };

  switch (type) {
    case 'SINGLE':
      newProps.component = SingleQuestion;
      break;
    default: throw new Error(`Unexpected question type: ${type}`);
  }

  return newProps;
});

export default enhance(Question);
