import { injectGlobal } from 'styled-components';

import 'font-awesome/css/font-awesome.css';
import './index.scss';

const COLORS = {
  cyan1: '#57c4c6',
  orange1: '#ffaa47',
  gray1: '#999',
  lightGray1: '#f5f5f5',
};

export const theme = {
  color: {
    cyan: COLORS.cyan1,
    orange: COLORS.orange1,
    gray: COLORS.gray1,
    lighGray: COLORS.lightGray1,
  },
};

export const GMAP_STYLE = [
  {
    featureType: 'landscape.natural',
    elementType: 'geometry.fill',
    stylers: [
      {
        visibility: 'on',
      },
      {
        color: '#e0efef',
      },
    ],
  },
  {
    featureType: 'poi',
    elementType: 'geometry.fill',
    stylers: [
      {
        visibility: 'on',
      },
      {
        hue: '#1900ff',
      },
      {
        color: '#c0e8e8',
      },
    ],
  },
  {
    featureType: 'road',
    elementType: 'geometry',
    stylers: [
      {
        lightness: 100,
      },
      {
        visibility: 'simplified',
      },
    ],
  },
  {
    featureType: 'road',
    elementType: 'labels',
    stylers: [
      {
        visibility: 'off',
      },
    ],
  },
  {
    featureType: 'transit.line',
    elementType: 'geometry',
    stylers: [
      {
        visibility: 'on',
      },
      {
        lightness: 700,
      },
    ],
  },
  {
    featureType: 'water',
    elementType: 'all',
    stylers: [
      {
        color: '#7dcdcd',
      },
    ],
  },
];

/* eslint-disable no-unused-expressions */
injectGlobal`
  html {
    background-color: ${COLORS.cyan1};
  }

  .c-orange {
    color: ${COLORS.orange1};
  }

  .c-gray {
    color: ${COLORS.gray1};
  }
`;
/* eslint-enable no-unused-expressions */
