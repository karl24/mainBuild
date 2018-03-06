/**
 *
 * AdminPosts
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
import makeSelectAdminPosts from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export class AdminPosts extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <Helmet>
          <title>AdminPosts</title>
          <meta name="description" content="Description of AdminPosts" />
        </Helmet>
        <FormattedMessage {...messages.header} />
      </div>
    );
  }
}

AdminPosts.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  adminposts: makeSelectAdminPosts(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(mapStateToProps, mapDispatchToProps);

const withReducer = injectReducer({ key: 'adminPosts', reducer });
const withSaga = injectSaga({ key: 'adminPosts', saga });

export default compose(
  withReducer,
  withSaga,
  withConnect,
)(AdminPosts);
