
import { fromJS } from 'immutable';
import studentSignedInLandingPageReducer from '../reducer';

describe('studentSignedInLandingPageReducer', () => {
  it('returns the initial state', () => {
    expect(studentSignedInLandingPageReducer(undefined, {})).toEqual(fromJS({}));
  });
});
