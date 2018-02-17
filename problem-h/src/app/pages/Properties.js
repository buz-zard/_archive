import React from 'react';

import { Page, Loading } from '../components';

function Home() {
  return (
    <Page className="pa3 ph4-ns pt4 pb5">
      <h1>Your properties</h1>
      <Loading />
    </Page>
  );
}

export default Home;
