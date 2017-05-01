import React from 'react';
import PropTypes from 'prop-types';
import uuid from 'uuid/v1';

import {Container} from './Question';


class SingleQuestion extends React.Component {

  state = {answer: null}

  onSelect = (value) => {
    this.setState({answer: value});
  }

  onSubmit = () => {
    this.props.onSubmit(this.state.answer);
  }

  render() {
    const {number, label, options, last, ...props} = this.props;
    const {answer} = this.state;
    return (
      <Container {...props}>
        <div className='mb2'>{number}. {label}</div>
        <div>
          {options.map((item) => {
            const _id = uuid();
            return (
              <div key={item.value} className='mv1'>
                <input
                  id={_id}
                  type='radio'
                  name={`question_${number}`}
                  checked={answer === item.value}
                  onChange={() => this.onSelect(item.value)}
                />
                <label className='ml2' htmlFor={_id}>{item.label}</label>
              </div>
            );
          })}
        </div>
        <div>
          <button type='button' className='mt2' disabled={answer == null} onClick={this.onSubmit}>
            {last ? 'Submit' : 'Next'}
          </button>
        </div>
      </Container>
    );
  }
}

SingleQuestion.propTypes = {
  number: PropTypes.number.isRequired,
  label: PropTypes.string.isRequired,
  options: PropTypes.arrayOf(PropTypes.shape({
    value: PropTypes.number.isRequired,
    label: PropTypes.string.isRequired,
  })).isRequired,
  onSubmit: PropTypes.func.isRequired,
  last: PropTypes.bool.isRequired,
};

export default SingleQuestion;
