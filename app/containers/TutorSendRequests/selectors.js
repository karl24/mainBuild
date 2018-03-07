import { createSelector } from 'reselect';

/**
 * Direct selector to the tutorSendRequests state domain
 */
const selectTutorSendRequestsDomain = (state) => state.get('tutorSendRequests');

/**
 * Other specific selectors
 */


/**
 * Default selector used by TutorSendRequests
 */

const makeSelectTutorSendRequests = () => createSelector(
  selectTutorSendRequestsDomain,
  (substate) => substate.toJS()
);

export default makeSelectTutorSendRequests;
export {
  selectTutorSendRequestsDomain,
};
