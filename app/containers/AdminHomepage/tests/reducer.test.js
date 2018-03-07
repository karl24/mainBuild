
import { fromJS } from 'immutable';
import adminHomepageReducer from '../reducer';

describe('adminHomepageReducer', () => {
  it('returns the initial state', () => {
    expect(adminHomepageReducer(undefined, {})).toEqual(fromJS({}));
  });
});
