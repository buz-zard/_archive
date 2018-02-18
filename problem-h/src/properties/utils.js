import properties from './data/properties.json';

const getRandomInt = (min, max) =>
  Math.floor(Math.random() * (max - min + 1)) + min;

const mockResponse = data =>
  new Promise(resolve => {
    setTimeout(resolve.bind(null, data), getRandomInt(100, 800));
  });

export const getProperties = () => mockResponse(properties);
