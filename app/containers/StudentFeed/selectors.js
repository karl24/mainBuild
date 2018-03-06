import { createSelector } from 'reselect';

/**
 * Direct selector to the studentFeed state domain
 */
const selectStudentFeedDomain = (state) => state.get('studentFeed');

/**
 * Other specific selectors
 */


/**
 * Default selector used by StudentFeed
 */

const makeSelectStudentFeed = () => createSelector(
  selectStudentFeedDomain,
  (substate) => substate.toJS()
);

export default makeSelectStudentFeed;
export {
  selectStudentFeedDomain,
};
