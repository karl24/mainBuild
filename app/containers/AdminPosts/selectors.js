import { createSelector } from 'reselect';

/**
 * Direct selector to the adminPosts state domain
 */
const selectAdminPostsDomain = (state) => state.get('adminPosts');

/**
 * Other specific selectors
 */


/**
 * Default selector used by AdminPosts
 */

const makeSelectAdminPosts = () => createSelector(
  selectAdminPostsDomain,
  (substate) => substate.toJS()
);

export default makeSelectAdminPosts;
export {
  selectAdminPostsDomain,
};
