import { createSelector } from 'reselect';

/**
 * Direct selector to the studentProfile state domain
 */
const selectStudentProfileDomain = (state) => state.get('studentProfile');

/**
 * Other specific selectors
 */


/**
 * Default selector used by StudentProfile
 */

const makeSelectStudentProfile = () => createSelector(
  selectStudentProfileDomain,
  (substate) => substate.toJS()
);

export default makeSelectStudentProfile;
export {
  selectStudentProfileDomain,
};
