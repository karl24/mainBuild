
import { fromJS } from 'immutable';
import adminPostsReducer from '../reducer';

describe('adminPostsReducer', () => {
  it('returns the initial state', () => {
    expect(adminPostsReducer(undefined, {})).toEqual(fromJS({}));
  });
});
