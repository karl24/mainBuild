/**
 *
 * Asynchronously loads the component for AdminFeed
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
