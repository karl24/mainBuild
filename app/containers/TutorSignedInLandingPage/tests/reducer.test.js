
import { fromJS } from 'immutable';
import tutorSignedInLandingPageReducer from '../reducer';

describe('tutorSignedInLandingPageReducer', () => {
  it('returns the initial state', () => {
    expect(tutorSignedInLandingPageReducer(undefined, {})).toEqual(fromJS({}));
  });
});
