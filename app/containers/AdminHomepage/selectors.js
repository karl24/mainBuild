import { createSelector } from 'reselect';

/**
 * Direct selector to the adminHomepage state domain
 */
const selectAdminHomepageDomain = (state) => state.get('adminHomepage');

/**
 * Other specific selectors
 */


/**
 * Default selector used by AdminHomepage
 */

const makeSelectAdminHomepage = () => createSelector(
  selectAdminHomepageDomain,
  (substate) => substate.toJS()
);

export default makeSelectAdminHomepage;
export {
  selectAdminHomepageDomain,
};
