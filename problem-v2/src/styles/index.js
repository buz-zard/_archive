import 'normalize.css/normalize.css';
import 'font-awesome/css/font-awesome.css';

import cfg from '../config';
import './index.css';


export const getTheme = () => ({
  breakpoints: {
    tablet: `min-width: ${cfg.breakpoints.tablet}px`,
    desktop: `min-width: ${cfg.breakpoints.desktop}px`,
  },
});
