import React from 'react';
import styled from 'styled-components';

import api from '../api';
import Cards from './Cards';


const Container = styled.div`
  background-color: #e8e8e8;
  height: 100%;
  overflow-y: auto;
`;


class App extends React.Component {

  state = {items: null}

  componentDidMount() {
    api.getShots().then((data = []) => {
      this.setState({items: data.map(item => ({
        id: item.id,
        url: item.html_url,
        image: item.images.teaser,
        title: item.title,
      }))});
    });
  }

  render() {
    const {items} = this.state;
    if (items && items.length) {
      return (
        <Container>
          <Cards items={items} />
        </Container>
      );
    }
    return null;
  }
}

export default App;
