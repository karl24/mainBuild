import { createSelector } from 'reselect';

/**
 * Direct selector to the studentSignedInLandingPage state domain
 */
const selectStudentSignedInLandingPageDomain = (state) => state.get('studentSignedInLandingPage');

/**
 * Other specific selectors
 */


/**
 * Default selector used by StudentSignedInLandingPage
 */

const makeSelectStudentSignedInLandingPage = () => createSelector(
  selectStudentSignedInLandingPageDomain,
  (substate) => substate.toJS()
);

export default makeSelectStudentSignedInLandingPage;
export {
  selectStudentSignedInLandingPageDomain,
};
