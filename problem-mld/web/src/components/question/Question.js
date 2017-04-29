import {compose, branch, renderComponent, renderNothing} from 'recompose';

import SingleQuestion from './SingleQuestion';
import MultiQuestion from './MultiQuestion';


const Question = compose(
  branch(props => props.type === 'single', renderComponent(SingleQuestion)),
  branch(props => props.type === 'multi', renderComponent(MultiQuestion)),
)(renderNothing());

export default Question;
