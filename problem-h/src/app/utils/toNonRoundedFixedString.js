const toNonRoundedFixedString = (number, precision) => {
  if (precision) {
    const str = number.toFixed(precision + 1);
    return str.slice(0, str.length - 1);
  }
  const str = number.toFixed(1);
  return str.slice(0, str.length - 2);
};

export default toNonRoundedFixedString;
