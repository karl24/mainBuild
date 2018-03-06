
import { fromJS } from 'immutable';
import studentProfileReducer from '../reducer';

describe('studentProfileReducer', () => {
  it('returns the initial state', () => {
    expect(studentProfileReducer(undefined, {})).toEqual(fromJS({}));
  });
});
