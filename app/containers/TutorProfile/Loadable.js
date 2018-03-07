/**
 *
 * Asynchronously loads the component for TutorProfile
 *
 */

import Loadable from 'react-loadable';

export default Loadable({
  loader: () => import('./index'),
  loading: () => null,
});
