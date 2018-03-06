import { createSelector } from 'reselect';

/**
 * Direct selector to the tutorProfile state domain
 */
const selectTutorProfileDomain = (state) => state.get('tutorProfile');

/**
 * Other specific selectors
 */


/**
 * Default selector used by TutorProfile
 */

const makeSelectTutorProfile = () => createSelector(
  selectTutorProfileDomain,
  (substate) => substate.toJS()
);

export default makeSelectTutorProfile;
export {
  selectTutorProfileDomain,
};
