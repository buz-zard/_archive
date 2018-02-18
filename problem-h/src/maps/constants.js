export const NAME = 'maps';

export const API_KEY = 'AIzaSyAl3mueg_hOBENcK33AwEjC5_ilseBw18c';
export const MAP_LIBRARIES = ['geometry', 'drawing', 'places'];
export const MAP_API_URL = `https://maps.googleapis.com/maps/api/js?key=${API_KEY}&v=3.exp&libraries=${MAP_LIBRARIES.join(
  ','
)}`;
