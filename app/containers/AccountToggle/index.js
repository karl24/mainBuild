/*
 *
 * LanguageToggle
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createSelector } from 'reselect';

import Toggle from 'components/Toggle';
import Wrapper from './Wrapper';
import messages from './messages';
//import { appLocales } from '../../i18n';
import { changeToggle } from './actions';
//import { makeSelectLocale } from '../LanguageProvider/selectors';

const accountTypes = [
  'Student',
  'Tutor',
];

export class AccountToggle extends React.PureComponent { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <Wrapper>
        <Toggle value={this.props.account} values={accountTypes} messages={messages} onToggle={this.props.onToggle} />
      </Wrapper>
    );
  }
}

LocaleToggle.propTypes = {
  onToggle: PropTypes.func,
  account: PropTypes.string,
};

const mapStateToProps = createSelector(
  makeSelectLocale(),
  (locale) => ({ locale })
);

export function mapDispatchToProps(dispatch) {
  return {
    onToggle: (evt) => dispatch(changeToggle(evt.target.value)),
    dispatch,
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LocaleToggle);
