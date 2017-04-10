import store from './store';
import '../styles';


export const init = () => null;

export {store};

export default {
  ACCESS_TOKEN: '0059fb27e12aeb7704b1f0c53daeb7d277f1912b2d58af1afed61f9d04bd98a5',
  infinityList: {
    updateThreshold: { // Request update when x rows left to bottom
      phone: 2.2,
      tablet: 2.6,
      desktop: 3,
    },
    pagesToRequest: { // How many pages of shots to request
      phone: 1.5,
      tablet: 1.4,
      desktop: 1.3,
    },
  },
};
