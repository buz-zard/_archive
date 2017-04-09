export const ADDED_TO_FAVOURITES = 'shots/ADDED_TO_FAVOURITES';
export const REMOVED_FROM_FAVOURITES = 'shots/REMOVED_FROM_FAVOURITES';


export const addToFavourites = id => ({
  type: ADDED_TO_FAVOURITES,
  payload: id,
});

export const removeFromFavourites = id => ({
  type: REMOVED_FROM_FAVOURITES,
  payload: id,
});
