import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Sticky from 'react-sticky-el';

import { FAIcon, HeaderLink } from './';

const Logo = styled(Link)`
  text-decoration: none;
  color: black;
`;

const Container = styled.header`
  .Header {
    background: white;
    transition: background 300ms ease-out;

    .Header__Logo {
      transition: font-size 300ms ease-out;
    }

    .Header__Link {
      transition: color 300ms ease-out;
    }
  }

  .sticky {
    .Header {
      background: ${props => props.theme.color.cyan};

      .Header__Logo {
        font-size: 1rem;
      }

      .Header__Link {
        color: white;
      }
    }
  }
`;

class Header extends React.Component {
  componentDidMount() {
    window.scroll(window.scrollX, window.scrollY + 1);
    window.scroll(window.scrollX, window.scrollY - 1);
  }

  render() {
    return (
      <Container>
        <Sticky stickyStyle={{ zIndex: 1 }}>
          <div className="Header pa3 ph4-ns">
            <div className="flex items-center">
              <div className="flex-grow-1">
                <Logo to="/" className="f4 Header__Logo">
                  <FAIcon type="home" />
                  <span> BNBADMIN</span>
                </Logo>
              </div>
              <HeaderLink exact to="/" className="nl2">
                Home
              </HeaderLink>
              <HeaderLink to="/properties" className="nr2">
                My Properties
              </HeaderLink>
            </div>
          </div>
        </Sticky>
      </Container>
    );
  }
}

export default Header;
