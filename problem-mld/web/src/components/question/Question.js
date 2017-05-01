import {compose, branch, renderComponent, renderNothing} from 'recompose';
import styled, {keyframes} from 'styled-components';

import SingleQuestion from './SingleQuestion';
import MultiQuestion from './MultiQuestion';


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


const Question = compose(
  branch(props => props.type === 'single', renderComponent(SingleQuestion)),
  branch(props => props.type === 'multi', renderComponent(MultiQuestion)),
)(renderNothing());

export default Question;
