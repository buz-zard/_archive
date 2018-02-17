import toNonRoundedFixedString from './toNonRoundedFixedString';

describe('toNonRoundedFixedString', () => {
  it('should round correctly', () => {
    expect(toNonRoundedFixedString(1.369, 2)).toEqual('1.36');
    expect(toNonRoundedFixedString(1.9, 2)).toEqual('1.90');
    expect(toNonRoundedFixedString(1.9, 0)).toEqual('1');
  });
});
