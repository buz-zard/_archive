import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Sticky from 'react-stickynode';

import { FAIcon, HeaderLink } from './';

const Logo = styled(Link)`
  text-decoration: none;
  color: black;
`;

const Container = styled.header`
  .sticky-outer-wrapper {
    .sticky-inner-wrapper {
      background: white;
      transition: background 300ms ease-out;

      .Logo {
        transition: font-size 300ms ease-out;
      }

      .HeaderLink {
        transition: color 300ms ease-out;
      }
    }

    &.active {
      .sticky-inner-wrapper {
        background: ${props => props.theme.color.cyan};

        .Logo {
          font-size: 1rem;
        }

        .HeaderLink {
          color: white;
        }
      }
    }
  }
`;

class Header extends React.Component {
  componentDidMount() {
    setTimeout(() => {
      window.scroll(window.scrollX, window.scrollY + 1);
      window.scroll(window.scrollX, window.scrollY - 1);
    });
  }

  render() {
    return (
      <Container>
        <Sticky>
          <div className="pa3 ph4-ns">
            <div className="flex items-center">
              <div className="flex-grow-1">
                <Logo to="/" className="f4 Logo">
                  <FAIcon type="home" />
                  <span> BNBADMIN</span>
                </Logo>
              </div>
              <HeaderLink exact to="/" className="nl2 HeaderLink">
                Home
              </HeaderLink>
              <HeaderLink to="/properties" className="nr2 HeaderLink">
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
