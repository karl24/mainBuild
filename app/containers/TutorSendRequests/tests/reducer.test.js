
import { fromJS } from 'immutable';
import tutorSendRequestsReducer from '../reducer';

describe('tutorSendRequestsReducer', () => {
  it('returns the initial state', () => {
    expect(tutorSendRequestsReducer(undefined, {})).toEqual(fromJS({}));
  });
});
