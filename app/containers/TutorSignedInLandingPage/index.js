/**
 *
 * TutorSignedInLandingPage
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
import makeSelectTutorSignedInLandingPage from './selectors';
import reducer from './reducer';
import saga from './saga';
import messages from './messages';

export class TutorSignedInLandingPage extends React.Component { // eslint-disable-line react/prefer-stateless-function
  render() {
    return (
      <div>
        <Helmet>
          <title>TutorSignedInLandingPage</title>
          <meta name="description" content="Description of TutorSignedInLandingPage" />
        </Helmet>
        <FormattedMessage {...messages.header} />
      </div>
    );
  }
}

TutorSignedInLandingPage.propTypes = {
  dispatch: PropTypes.func.isRequired,
};

const mapStateToProps = createStructuredSelector({
  tutorsignedinlandingpage: makeSelectTutorSignedInLandingPage(),
});

function mapDispatchToProps(dispatch) {
  return {
    dispatch,
  };
}

const withConnect = connect(mapStateToProps, mapDispatchToProps);

const withReducer = injectReducer({ key: 'tutorSignedInLandingPage', reducer });
const withSaga = injectSaga({ key: 'tutorSignedInLandingPage', saga });

export default compose(
  withReducer,
  withSaga,
  withConnect,
)(TutorSignedInLandingPage);
