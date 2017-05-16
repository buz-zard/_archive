import React from 'react';
import PropTypes from 'prop-types';
import {Link} from 'react-router-dom';
import {withProps} from 'recompose';
import styled from 'styled-components';

import api from 'src/api';
import {Icon, LoadingIndicator} from './';


const StyledLink = styled(Link)`
  text-decoration: none;
  color: ${props => props.theme.color.accent};
`;


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
        <p>Test you skills in one of the quizes</p>
        <div className='ph3'>
          {items.map(item =>
            <div key={item.id} className='mv1'>
              <Icon className='fa-hand-o-right mr2' />
              <StyledLink to={`/quiz/${item.id}`}>{item.name}</StyledLink>
            </div>,
          )}
        </div>
      </div>
    );
  }
}

Questionaires.propTypes = {
  getData: PropTypes.func.isRequired,
};


const enhance = withProps({getData: api.getQuestionaires});

export default enhance(Questionaires);
