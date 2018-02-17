import React from 'react';

import { FAIcon } from './';

function Loading() {
  return (
    <div className="pa2">
      <FAIcon type="circle-o-notch" className="fa-spin fa-2x fa-fw center db" />
    </div>
  );
}

export default Loading;
