import React from 'react';
import PropTypes from 'prop-types';
import {Link} from 'react-router-dom';
import {withProps} from 'recompose';

import api from 'src/api';
import LoadingIndicator from './LoadingIndicator';


class Questionaires extends React.Component {

  state = {items: []}

  componentDidMount() {
    this.props.getData().then(data => this.setState({items: data}));
  }

  render() {
    const {items} = this.state;
    if (!items.length) return <LoadingIndicator />;
    return (
      <div>
        <p>Test you skills in one of these quizes</p>
        <ul>
          {items.map(item =>
            <li key={item.id}>
              <Link to={`/quiz/${item.id}`}>{item.label}</Link>
            </li>,
          )}
        </ul>
      </div>
    );
  }
}

Questionaires.propTypes = {
  getData: PropTypes.func.isRequired,
};


const enhance = withProps({getData: api.getQuestionaires});

export default enhance(Questionaires);
