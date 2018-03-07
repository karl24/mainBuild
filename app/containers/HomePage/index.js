/*
 * HomePage
 *
 * This is the first thing users see of our App, at the '/' route
 */

import React from 'react';
import { Helmet } from 'react-helmet';
import { FormattedMessage } from 'react-intl';
// import PropTypes from 'prop-types';

import CenteredSection from './CenteredSection';
import Form from './Form';
import Input from './Input';
// import Section from './Section';
import messages from './messages';
import Wrapper from './Wrapper';
// import Modal from './Modal';
import Select from './Select';

// import A from 'components/A';
// import H2 from 'components/H2';
// import H1 from 'components/H1';
import Button from 'components/Button';
// import Toggle from 'components/Toggle';
// import ToggleOption from 'components/ToggleOption';

// import AccountToggle from 'containers/AccountToggle';
// import AccountToggle from './AccountToggle';

//function submitSignUp(props){
//	  return (<p> signed up </p>)
//  }

//const {accountTypes} = ['Student','Tutor',];
  
export default class HomePage extends React.Component { // eslint-disable-line react/prefer-stateless-function

	constructor() {
    super();
    this.handleSubmit = this.handleSubmit.bind(this); //for submit button
}

	handleSubmit(event) { // submission event when sign up is clicked
    event.preventDefault();
    if (!event.target.checkValidity()) {
    	this.setState({
        invalid: true,
        displayErrors: true,
      });
      return;
    }
    const form = event.target;
    const data = new FormData(form);

		for (let name of data.keys()) {
			const input = form.elements[name];
			const parserName = input.dataset.parse;
			console.log('parser name is', parserName);
			if (parserName) {
				const parsedValue = inputParsers[parserName](data.get(name))
				data.set(name, parsedValue);
			}
		}
	}

  // Since state and props are static,
  // there's no need to re-render this component
  shouldComponentUpdate() {
    return false;
  }

  render() {

    return (
      <article>
        <Helmet>
          <title>TutorFind</title>
          <meta name="Tutorfind" content="A web app to connect students and teachers for improved learning" />
        </Helmet>
		
        <div>
		<Wrapper>
		
		  {/* About Us */}
          <CenteredSection>
            <H1>
              About Us
            </H1>
            <p>
              <FormattedMessage {...messages.aboutMessage} />
            </p>
          </CenteredSection>
		  {/* end About Us */}
		  
		  {/* Sign up */}
          <CenteredSection>
			<H1>
			  Sign Up
			</H1>
			
			{/* Form */}
			<Form onSubmit={this.handleSubmit}>
			  <div>
              <label htmlFor="username">
                <Input
                  id="username"
                  type="text"
                  placeholder="User Name"
                  value={this.props.username}
                  onChange={this.props.onChangeUsername}
				  //required
                />
              </label>
			  </div>
			  
			  <div>
			  <label htmlFor="email">
                <Input
                  id="email"
                  type="email"
                  placeholder="Email"
                  value={this.props.email}
                  onChange={this.props.onChangeEmail}
				  //required
                />
              </label>
			  </div>
			  
			  <div>
			  <label htmlFor="password">
                <Input
                  id="password"
                  type="password"
                  placeholder="Password"
                  value={this.props.password}
                  onChange={this.props.onChangePassword}
				  //required
                />
              </label>
			  </div>
			  
			  <div>
			  <label htmlFor="confirmPassword">
                <Input
                  id="password"
                  type="password"
                  placeholder="Confirm Password"
                  value={this.props.password}
                  onChange={this.props.onChangePassword}
				  //required
                />
              </label>
			  </div>
			  
			  <div>
			    <p> I am a 
					 <Select>
						<option value="Student">Student</option>
						<option value="Tutor">Tutor</option>
					</Select> 
					
				</p>
			  </div>
			  
			  <div>
				<Button> Sign Up </Button>
			  </div>
			  
			  {/*
			  <div>
				<Input
				  type="submit"
				  value="Submit"
				/>
			  </div> */}
			  
			</Form>
			{/* end Form */}
			
		   </CenteredSection>  
		   {/* end Sign up */}
		   
		</Wrapper>	
        </div>
      </article>
    );
  }
}
