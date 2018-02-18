export const makeAddressKey = ({ postCode, city, country }) =>
  [postCode, city, country].filter(Boolean).join(', ');
