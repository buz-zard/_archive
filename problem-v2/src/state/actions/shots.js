import api from '../../api';


export const ITEMS_INITIALIZED = 'shots/ITEMS_INITIALIZED';
export const ITEMS_LOADING_STARTED = 'shots/ITEMS_LOADING_STARTED';
export const ITEMS_LOADING_FINISHED = 'shots/ITEMS_LOADING_FINISHED';
export const ADDED_TO_FAVOURITES = 'shots/ADDED_TO_FAVOURITES';
export const REMOVED_FROM_FAVOURITES = 'shots/REMOVED_FROM_FAVOURITES';


export const initializeItems = ({pageSize}) => ({
  type: ITEMS_INITIALIZED,
  payload: {pageSize},
});

export const startItemsLoading = ({page}) => ({
  type: ITEMS_LOADING_STARTED,
  payload: {page},
});

export const finishItemsLoading = ({page, data}) => ({
  type: ITEMS_LOADING_FINISHED,
  payload: {page, data},
});

export const addToFavourites = id => ({
  type: ADDED_TO_FAVOURITES,
  payload: id,
});

export const removeFromFavourites = id => ({
  type: REMOVED_FROM_FAVOURITES,
  payload: id,
});


// async
export const loadItems = () => (dispatch, getState) => new Promise((resolve, reject) => {
  const {shots: {list: {loading, page, pageSize}}} = getState();
  if (loading || pageSize == null) {
    resolve();
    return;
  }

  dispatch(startItemsLoading({page: page + 1}));
  api.getShots(page + 1, pageSize).then((data) => {
    const result = data.map(item => ({
      id: item.id,
      url: item.html_url,
      image: item.images.teaser,
      title: item.title,
    }));
    dispatch(finishItemsLoading({page: page + 1, data: result}));
    resolve();
  }).catch(reject);
});
