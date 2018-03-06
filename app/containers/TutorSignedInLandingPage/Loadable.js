/**
 *
 * Asynchronously loads the component for TutorSignedInLandingPage
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
