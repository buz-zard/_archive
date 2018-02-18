import React from 'react';

import { Page, Article, PropertyFocus } from '../components';
import { FOCUS_AREA } from '../constants';

function Home() {
  return (
    <Page className="pa3 ph4-ns pt4 pb5">
      <Article title="Let's focus!">
        <p>
          We have decided to contentrate more on the central properties. So take
          a look at the map below and identify which properties are essential.
        </p>
        <PropertyFocus area={FOCUS_AREA} zoom={12} />
      </Article>
      <Article title="Hello world">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </p>
      </Article>
      <Article title="Some news">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
          culpa qui officia deserunt mollit anim id est laborum.
        </p>
        <ul>
          <li className="mv2">Stuff 1</li>
          <li className="mv2">Stuff 2</li>
          <li className="mv2">Stuff 3</li>
        </ul>
      </Article>
      <Article title="Some insights">
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat. Duis aute irure dolor in
          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
          pariatur.
        </p>
      </Article>
    </Page>
  );
}

export default Home;
