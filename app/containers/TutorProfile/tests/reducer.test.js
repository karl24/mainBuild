
import { fromJS } from 'immutable';
import tutorProfileReducer from '../reducer';

describe('tutorProfileReducer', () => {
  it('returns the initial state', () => {
    expect(tutorProfileReducer(undefined, {})).toEqual(fromJS({}));
  });
});
