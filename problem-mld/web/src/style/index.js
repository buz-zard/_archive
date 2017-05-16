import 'font-awesome/css/font-awesome.css';
import {injectGlobal} from 'styled-components';

import './_tachyons.css';


const BLOKK_FONT = `${process.env.PUBLIC_URL}/fonts/BLOKKNeue-Regular`;

/* eslint-disable */
injectGlobal`
  @font-face {
    font-family: 'BLOKK';
    src: url(${`${BLOKK_FONT}.eot`});
    src: url(${`${BLOKK_FONT}.eot?#iefix`}) format('embedded-opentype'),
        url(${`${BLOKK_FONT}.woff2`}) format('woff2'),
        url(${`${BLOKK_FONT}.woff`}) format('woff'),
        url(${`${BLOKK_FONT}.otf`}) format('opentype'),
        url(${`${BLOKK_FONT}.ttf`}) format('truetype'),
        url(${`${BLOKK_FONT}.svg#BLOKKRegular`}) format('svg');
    font-weight: normal;
    font-style: normal;
  }

  html, #root {
    height: 100%;
  }
  
  body {
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    margin: 0;

    font-family: 'Roboto', sans-serif;
    font-size: 18px;
    color: #2c3e50;
    height: 100%;
  }

  * {
    box-sizing: border-box;
  }
`;
/* eslint-enable */


export const getTheme = () => ({
  color: {
    text: '#2c3e50',
    accent: '#8e44ad',
  },
});
