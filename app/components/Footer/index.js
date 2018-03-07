import React from 'react';
import { FormattedMessage } from 'react-intl';
import PropTypes from 'prop-types';

import A from 'components/A';
import LocaleToggle from 'containers/LocaleToggle';
import Wrapper from './Wrapper';
import messages from './messages';
import H2 from 'components/H2';
import List from './List';
import ListItem from './ListItem';

function Footer() {
  return (
  <div>
	<Wrapper>
		<section>
		  <H2> About Us </H2>
		  <p>We are fantastic and our project is awesome!</p>
		</section>
		<section>
		  <H2> Navigation </H2>
		  <List>
			<ListItem>
				<A href="/"> Home </A>
			</ListItem>
			<ListItem>
				<A href="/features"> Features </A>
			</ListItem>
			<ListItem>
				<A href="/StudentProfile"> Profile </A>
			</ListItem>
		  </List>
		</section>
		<section>
	      <H2> Contact Us </H2>
		  <List>
			<ListItem>
				<A href="mailto:info@tutorfind.com">info@tutorfind.com</A>
			</ListItem>
			<ListItem>
				<p> (408) 123-2018 </p>
			</ListItem>
		  </List>
		</section>
	</Wrapper>
    
	
    <Wrapper>
      <section>
        <FormattedMessage {...messages.copyrightMessage} />
      </section>
	  {/* max stoiber 
      <section>
        <LocaleToggle />
      </section>
      <section>
        <FormattedMessage
          {...messages.authorMessage}
          values={{
            author: <A href="https://twitter.com/mxstbr">Max Stoiber</A>,
          }}
        />
      </section>
	  */}
    </Wrapper>
	
  </div>	
  );
}

export default Footer;
