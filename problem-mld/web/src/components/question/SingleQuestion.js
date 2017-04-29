import React from 'react';
import PropTypes from 'prop-types';
import uuid from 'uuid/v1';


class SingleQuestion extends React.Component {

  state = {answer: null}

  onSelect = (value) => {
    this.setState({answer: value});
  }

  render() {
    const {index, label, options, ...props} = this.props;
    const {answer} = this.state;
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
                  type='radio'
                  name={`question_${index}`}
                  checked={answer === item.value}
                  onChange={() => this.onSelect(item.value)}
                />
                <label className='ml2' htmlFor={_id}>{item.label}</label>
              </div>
            );
          })}
        </div>
        <div>
          <button type='button' className='mt2' disabled={answer == null}>Next</button>
        </div>
      </div>
    );
  }
}

SingleQuestion.propTypes = {
  index: PropTypes.number.isRequired,
  label: PropTypes.string.isRequired,
  options: PropTypes.arrayOf(PropTypes.shape({
    value: PropTypes.number.isRequired,
    label: PropTypes.string.isRequired,
  })).isRequired,
};

export default SingleQuestion;
