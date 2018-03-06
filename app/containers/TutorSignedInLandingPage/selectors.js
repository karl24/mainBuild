import { createSelector } from 'reselect';

/**
 * Direct selector to the tutorSignedInLandingPage state domain
 */
const selectTutorSignedInLandingPageDomain = (state) => state.get('tutorSignedInLandingPage');

/**
 * Other specific selectors
 */


/**
 * Default selector used by TutorSignedInLandingPage
 */

const makeSelectTutorSignedInLandingPage = () => createSelector(
  selectTutorSignedInLandingPageDomain,
  (substate) => substate.toJS()
);

export default makeSelectTutorSignedInLandingPage;
export {
  selectTutorSignedInLandingPageDomain,
};
