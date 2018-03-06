/**
 *
 * AdminInbox
 *
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';

import injectSaga from 'utils/injectSaga';
import injectReducer from 'utils/injectReducer';
import makeSelectAdminInbox from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export class AdminInbox extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <Helmet>
          <title>AdminInbox</title>
          <meta name="description" content="Description of AdminInbox" />
        </Helmet>
        <FormattedMessage {...messages.header} />
      </div>
    );
  }
}

AdminInbox.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  admininbox: makeSelectAdminInbox(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(mapStateToProps, mapDispatchToProps);

const withReducer = injectReducer({ key: 'adminInbox', reducer });
const withSaga = injectSaga({ key: 'adminInbox', saga });

export default compose(
  withReducer,
  withSaga,
  withConnect,
)(AdminInbox);
