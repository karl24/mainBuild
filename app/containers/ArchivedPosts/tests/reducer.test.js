
import { fromJS } from 'immutable';
import archivedPostsReducer from '../reducer';

describe('archivedPostsReducer', () => {
  it('returns the initial state', () => {
    expect(archivedPostsReducer(undefined, {})).toEqual(fromJS({}));
  });
});
