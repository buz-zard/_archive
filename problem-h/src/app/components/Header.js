import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Sticky from 'react-sticky-el';
import cx from 'classnames';

import { FAIcon, HeaderLink } from './';

const Logo = styled(Link)`
  text-decoration: none;
  color: black;
`;

const Container = styled.header`
  .Header {
    background: white;

    &.animatable {
      transition: background 300ms ease-out;

      .Header__Logo {
        transition: font-size 300ms ease-out;
      }

      .Header__Link {
        transition: color 300ms ease-out;
      }
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
  state = { animatable: false };

  componentDidMount() {
    window.scroll(window.scrollX, window.scrollY + 1);
    window.scroll(window.scrollX, window.scrollY - 1);
    setTimeout(() => {
      this.setState({ animatable: true });
    });
  }

  render() {
    const { animatable } = this.state;
    return (
      <Container>
        <Sticky stickyStyle={{ zIndex: 1 }}>
          <div className={cx('Header pa3 ph4-ns', animatable && 'animatable')}>
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
