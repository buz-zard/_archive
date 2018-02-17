import React from 'react';

import { HeaderLink } from './';

function Header() {
  return (
    <header className="pa3 ph4-ns">
      <div className="flex">
        <div className="flex-grow-1">AirBNB Admin</div>
        <HeaderLink exact to="/" className="nl2">
          Home
        </HeaderLink>
        <HeaderLink to="/properties" className="nr2">
          My Properties
        </HeaderLink>
      </div>
    </header>
  );
}

export default Header;
