import React from 'react';
import PropTypes from 'prop-types';
import uuid from 'uuid/v1';


class MultiQuestion extends React.Component {

  state = {answers: []}

  onSelect = (value) => {
    const answers = this.state.answers.slice();
    const index = answers.indexOf(value);
    if (index === -1) {
      this.setState({answers: [value, ...answers]});
    } else {
      answers.splice(index, 1);
      this.setState({answers});
    }
  }

  render() {
    const {index, label, options, ...props} = this.props;
    const {answers} = this.state;
    return (
      <div {...props}>
        <div className='mb2'>{index}. {label}</div>
        <div>
          {options.map((item) => {
            const _id = uuid();
            return (
              <div key={item.value} className='mv1'>
                <input
                  id={_id}
                  type='checkbox'
                  name={`question_${index}`}
                  checked={answers.indexOf(item.value) >= 0}
                  onChange={() => this.onSelect(item.value)}
                />
                <label className='ml2' htmlFor={_id}>{item.label}</label>
              </div>
            );
          })}
        </div>
        <div>
          <button type='button' className='mt2' disabled={answers.length === 0}>Next</button>
        </div>
      </div>
    );
  }
}

MultiQuestion.propTypes = {
  index: PropTypes.number.isRequired,
  label: PropTypes.string.isRequired,
  options: PropTypes.arrayOf(PropTypes.shape({
    value: PropTypes.number.isRequired,
    label: PropTypes.string.isRequired,
  })).isRequired,
};

export default MultiQuestion;
