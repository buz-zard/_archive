import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <header className="pa3">
      <Link to="/">Home</Link>
      <Link to="/properties">My Properties</Link>
    </header>
  );
}

export default Header;
