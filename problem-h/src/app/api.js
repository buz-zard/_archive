import properties from './data/properties.json';
import { getRandomInt } from './utils';

const mockResponse = data =>
  new Promise(resolve => {
    setTimeout(resolve.bind(null, data), getRandomInt(100, 800));
  });

export default {
  getMyProperties() {
    return mockResponse(properties);
  },
};
