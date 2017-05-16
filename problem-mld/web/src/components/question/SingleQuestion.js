import React from 'react';
import PropTypes from 'prop-types';
import uuid from 'uuid/v1';

import {Button} from 'src/components';
import {Container} from './Question';
import QuestionName from './QuestionName';
import QuestionChoicesShell from './QuestionChoicesShell';


class SingleQuestion extends React.Component {

  state = {answer: null}

  componentWillUpdate(nextProps) {
    if (this.props.id !== nextProps.id) this.setState({answer: null});
  }

  onSelect = (value) => {
    this.setState({answer: value});
  }

  onSubmit = () => {
    this.props.onSubmit(this.state.answer);
  }

  render() {
    const {number, name, choices, loading, last, ...props} = this.props;
    const {answer} = this.state;
    return (
      <Container {...props}>
        <QuestionName number={number} name={name} />
        <div>
          {choices && choices.map((item) => {
            const _id = uuid();
            return (
              <div key={item.id} className='mv1'>
                <input
                  id={_id}
                  type='radio'
                  name={`question_${number}`}
                  checked={answer === item.id}
                  onChange={() => this.onSelect(item.id)}
                />
                <label className='ml2' htmlFor={_id}>{item.name}</label>
              </div>
            );
          })}
          {loading && <QuestionChoicesShell />}
        </div>
        <div>
          <Button className='mt3' disabled={answer == null} onClick={this.onSubmit}>
            {last ? 'Submit' : 'Next'}
          </Button>
        </div>
      </Container>
    );
  }
}

SingleQuestion.propTypes = {
  id: PropTypes.number.isRequired,
  number: PropTypes.number.isRequired,
  name: PropTypes.string.isRequired,
  choices: PropTypes.arrayOf(PropTypes.shape({
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
  })),
  loading: PropTypes.bool.isRequired,
  onSubmit: PropTypes.func.isRequired,
  last: PropTypes.bool.isRequired,
};

SingleQuestion.defaultProps = {
  choices: null,
};

export default SingleQuestion;
