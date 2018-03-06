
import { fromJS } from 'immutable';
import adminInboxReducer from '../reducer';

describe('adminInboxReducer', () => {
  it('returns the initial state', () => {
    expect(adminInboxReducer(undefined, {})).toEqual(fromJS({}));
  });
});
