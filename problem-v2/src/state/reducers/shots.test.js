import reducer from './shots';
import * as types from '../actions/shots';


// ########################################################################
// List
// ########################################################################
describe('shots list', () => {
  it('initializes list', () => {
    expect(reducer({}, {
      type: types.ITEMS_INITIALIZED,
      payload: {pageSize: 10},
    }).list).toEqual({
      loading: false,
      page: 0,
      pageSize: 10,
      data: [],
    });
  });

  it('starts loading with right parameters', () => {
    expect(reducer({list: {
      loading: false,
      page: 1,
      pageSize: 10,
      data: [],
    }}, {
      type: types.ITEMS_LOADING_STARTED,
      payload: {page: 1},
    }).list).toEqual({
      loading: false,
      page: 1,
      pageSize: 10,
      data: [],
    });
    expect(reducer({list: {
      loading: false,
      page: 1,
      pageSize: 10,
      data: [],
    }}, {
      type: types.ITEMS_LOADING_STARTED,
      payload: {page: 2},
    }).list).toEqual({
      loading: true,
      page: 2,
      pageSize: 10,
      data: [],
    });
  });

  it('finises loading with right parameters', () => {
    expect(reducer({list: {
      loading: true,
      page: 1,
      pageSize: 2,
      data: [],
    }}, {
      type: types.ITEMS_LOADING_FINISHED,
      payload: {page: 1, data: [{id: 31}, {id: 32}]},
    }).list).toEqual({
      loading: false,
      page: 1,
      pageSize: 2,
      data: [{id: 31}, {id: 32}],
    });
    expect(reducer({list: {
      loading: true,
      page: 2,
      pageSize: 2,
      data: [{id: 31}, {id: 32}],
    }}, {
      type: types.ITEMS_LOADING_FINISHED,
      payload: {page: 2, data: [{id: 33}, {id: 34}]},
    }).list).toEqual({
      loading: false,
      page: 2,
      pageSize: 2,
      data: [{id: 31}, {id: 32}, {id: 33}, {id: 34}],
    });
  });

  it('loading ending handles duplicates', () => {
    expect(reducer({list: {
      loading: true,
      page: 2,
      pageSize: 2,
      data: [{id: 31}, {id: 32}],
    }}, {
      type: types.ITEMS_LOADING_FINISHED,
      payload: {page: 2, data: [{id: 32}, {id: 33}]},
    }).list).toEqual({
      loading: false,
      page: 2,
      pageSize: 2,
      data: [{id: 31}, {id: 32}, {id: 33}],
    });
  });
});


// ########################################################################
// Favourites
// ########################################################################
describe('shots favourites', () => {
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
