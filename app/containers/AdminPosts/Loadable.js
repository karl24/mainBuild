/**
 *
 * Asynchronously loads the component for AdminPosts
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
