import { createSelector } from 'reselect';

/**
 * Direct selector to the archivedPosts state domain
 */
const selectArchivedPostsDomain = (state) => state.get('archivedPosts');

/**
 * Other specific selectors
 */


/**
 * Default selector used by ArchivedPosts
 */

const makeSelectArchivedPosts = () => createSelector(
  selectArchivedPostsDomain,
  (substate) => substate.toJS()
);

export default makeSelectArchivedPosts;
export {
  selectArchivedPostsDomain,
};
