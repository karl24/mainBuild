import React from 'react';
import { FormattedMessage } from 'react-intl';
import styled from 'styled-components';

import A from './A';
import Img from './Img';
import HeaderLink from './HeaderLink';
import logo from './tutorfindlogo.png';
import messages from './messages';
import Wrapper from './Wrapper';
import Button from 'components/Button';
import CenteredSection from './CenteredSection';
import Form from './Form';
import Input from './Input';
import H1 from 'components/H1';
import Modal from './Modal'

  //function submitSignIn(props){
  //	  return (<p> signed in </p>)
  //}

class Header extends React.Component { // eslint-disable-line react/prefer-stateless-function
	
	constructor(props) {
    super(props);
    this.state = { isOpen: false };  //whether the sign in modal is rendered
	this.handleSubmit = this.handleSubmit.bind(this); //for sign up submission
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
	
	toggleModal = () => { //opens and closes the sign in modal
    this.setState({
      isOpen: !this.state.isOpen
    });
  }

  render() {
    return (
      <div>
		<Wrapper>
			<section>
				<A href="/">
					<Img src={logo} alt="Tutorfind - Logo" />
				</A>
			</section>
			<section>
				<Button onClick={this.toggleModal}> Sign In </Button>
			</section>
		</Wrapper> 
		
		<CenteredSection>
			<Modal show={this.state.isOpen}
					onClose={this.toggleModal}>
					
					<H1>
					  Sign In
					</H1>
					
					{/*Form*/}
					<Form onSubmit={this.submitForm}>
					  <div>
						<label htmlFor="email">
						  <Input
							id="email"
							type="text"
							placeholder="Email"
							value={this.props.username}
							onChange={this.props.onChangeUsername}
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
							onChange={this.props.password}
							//required
						  />
						</label>
					  </div>
					</Form>
					{/*end Form*/}	

					<Button>
						Sign In
					</Button>
					<A href="/"> I forgot my password </A>
			</Modal>
		</CenteredSection>
      </div>
    );
  }
}

export default Header;
