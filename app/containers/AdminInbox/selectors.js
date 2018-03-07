import { createSelector } from 'reselect';

/**
 * Direct selector to the adminInbox state domain
 */
const selectAdminInboxDomain = (state) => state.get('adminInbox');

/**
 * Other specific selectors
 */


/**
 * Default selector used by AdminInbox
 */

const makeSelectAdminInbox = () => createSelector(
  selectAdminInboxDomain,
  (substate) => substate.toJS()
);

export default makeSelectAdminInbox;
export {
  selectAdminInboxDomain,
};
