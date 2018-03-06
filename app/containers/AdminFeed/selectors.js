import { createSelector } from 'reselect';

/**
 * Direct selector to the adminFeed state domain
 */
const selectAdminFeedDomain = (state) => state.get('adminFeed');

/**
 * Other specific selectors
 */


/**
 * Default selector used by AdminFeed
 */

const makeSelectAdminFeed = () => createSelector(
  selectAdminFeedDomain,
  (substate) => substate.toJS()
);

export default makeSelectAdminFeed;
export {
  selectAdminFeedDomain,
};
