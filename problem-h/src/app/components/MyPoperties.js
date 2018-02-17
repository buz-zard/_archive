import React from 'react';

import api from '../api';
import { Loading, Property } from '../components';

class MyPoperties extends React.Component {
  state = { data: undefined, hasLoaded: false };

  componentDidMount() {
    api
      .getMyProperties()
      .then(data => {
        this.setState({ data, hasLoaded: true });
      })
      .catch(() => {
        this.setState({ hasLoaded: true });
      });
  }

  render() {
    const { data, hasLoaded } = this.state;
    if (!hasLoaded) return <Loading />;
    if (!(data && data.length)) {
      return <p>You do not have any properties to manage</p>;
    }
    return data.map(item => <Property key={item.airbnbId} {...item} />);
  }
}

export default MyPoperties;
