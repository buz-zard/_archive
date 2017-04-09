import reducer from './shots';
import * as types from '../actions/shots';


describe('shot favourites', () => {
  it('should return the initial state', () => {
    expect(reducer(undefined, {}).favourites).toEqual([]);
  });

  it('should handle adding shot by id', () => {
    expect(reducer({favourites: []}, {
      type: types.ADDED_TO_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([10]);
    expect(reducer({favourites: [1]}, {
      type: types.ADDED_TO_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([1, 10]);
    expect(reducer({favourites: [1, 10]}, {
      type: types.ADDED_TO_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([1, 10]);
  });

  it('should handle removing shot by id', () => {
    expect(reducer({favourites: []}, {
      type: types.REMOVED_FROM_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([]);
    expect(reducer({favourites: [10, 25]}, {
      type: types.REMOVED_FROM_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([25]);
    expect(reducer({favourites: [10, 25]}, {
      type: types.REMOVED_FROM_FAVOURITES,
      payload: 25,
    }).favourites).toEqual([10]);
    expect(reducer({favourites: [10]}, {
      type: types.REMOVED_FROM_FAVOURITES,
      payload: 10,
    }).favourites).toEqual([]);
  });
});
