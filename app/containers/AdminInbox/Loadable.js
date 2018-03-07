/**
 *
 * Asynchronously loads the component for AdminInbox
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
