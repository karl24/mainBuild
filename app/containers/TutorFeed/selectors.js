import { createSelector } from 'reselect';

/**
 * Direct selector to the tutorFeed state domain
 */
const selectTutorFeedDomain = (state) => state.get('tutorFeed');

/**
 * Other specific selectors
 */


/**
 * Default selector used by TutorFeed
 */

const makeSelectTutorFeed = () => createSelector(
  selectTutorFeedDomain,
  (substate) => substate.toJS()
);

export default makeSelectTutorFeed;
export {
  selectTutorFeedDomain,
};
