
import { fromJS } from 'immutable';
import studentFeedReducer from '../reducer';

describe('studentFeedReducer', () => {
  it('returns the initial state', () => {
    expect(studentFeedReducer(undefined, {})).toEqual(fromJS({}));
  });
});
