import React from 'react';
import {shallow} from 'enzyme';

import {App} from './App';


it('renders without crashing', () => {
  shallow(
    <App
      initializeList={() => null}
      onItemsRequest={() => null}
      itemsLoading={false}
      getListParams={() => ({updateThreshold: 1, pageSize: 2})}
    />,
  );
});
