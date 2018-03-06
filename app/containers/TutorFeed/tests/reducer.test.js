
import { fromJS } from 'immutable';
import tutorFeedReducer from '../reducer';

describe('tutorFeedReducer', () => {
  it('returns the initial state', () => {
    expect(tutorFeedReducer(undefined, {})).toEqual(fromJS({}));
  });
});
