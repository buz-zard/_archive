import React from 'react';
import PropTypes from 'prop-types';
import uuid from 'uuid/v1';


class MultiQuestion extends React.Component {

  state = {answers: []}

  onSelect = (value) => {
    const answers = this.state.answers.slice();
    const number = answers.indexOf(value);
    if (number === -1) {
      this.setState({answers: [value, ...answers]});
    } else {
      answers.splice(number, 1);
      this.setState({answers});
    }
  }

  onSubmit = () => {
    this.props.onSubmit(this.state.answers);
  }

  render() {
    const {number, label, options, last, ...props} = this.props;
    const {answers} = this.state;
    return (
      <div {...props}>
        <div className='mb2'>{number}. {label}</div>
        <div>
          {options.map((item) => {
            const _id = uuid();
            return (
              <div key={item.value} className='mv1'>
                <input
                  id={_id}
                  type='checkbox'
                  name={`question_${number}`}
                  checked={answers.indexOf(item.value) >= 0}
                  onChange={() => this.onSelect(item.value)}
                />
                <label className='ml2' htmlFor={_id}>{item.label}</label>
              </div>
            );
          })}
        </div>
        <div>
          <button type='button' className='mt2' disabled={answers.length === 0} onClick={this.onSubmit}>
            {last ? 'Submit' : 'Next'}
          </button>
        </div>
      </div>
    );
  }
}

MultiQuestion.propTypes = {
  number: PropTypes.number.isRequired,
  label: PropTypes.string.isRequired,
  options: PropTypes.arrayOf(PropTypes.shape({
    value: PropTypes.number.isRequired,
    label: PropTypes.string.isRequired,
  })).isRequired,
  onSubmit: PropTypes.func.isRequired,
  last: PropTypes.bool.isRequired,
};

export default MultiQuestion;
