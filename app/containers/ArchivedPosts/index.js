/**
 *
 * ArchivedPosts
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
import makeSelectArchivedPosts from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export class ArchivedPosts extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <Helmet>
          <title>ArchivedPosts</title>
          <meta name="description" content="Description of ArchivedPosts" />
        </Helmet>
        <FormattedMessage {...messages.header} />
      </div>
    );
  }
}

ArchivedPosts.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  archivedposts: makeSelectArchivedPosts(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(mapStateToProps, mapDispatchToProps);

const withReducer = injectReducer({ key: 'archivedPosts', reducer });
const withSaga = injectSaga({ key: 'archivedPosts', saga });

export default compose(
  withReducer,
  withSaga,
  withConnect,
)(ArchivedPosts);
