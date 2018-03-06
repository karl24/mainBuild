/**
 *
 * Asynchronously loads the component for StudentSignedInLandingPage
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
