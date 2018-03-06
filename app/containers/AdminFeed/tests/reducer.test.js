
import { fromJS } from 'immutable';
import adminFeedReducer from '../reducer';

describe('adminFeedReducer', () => {
  it('returns the initial state', () => {
    expect(adminFeedReducer(undefined, {})).toEqual(fromJS({}));
  });
});
