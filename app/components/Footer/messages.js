/*
 * Footer Messages
 *
 * This contains all the text for the Footer component.
 */
import { defineMessages } from 'react-intl';

export default defineMessages({
  copyrightMessage: {
    id: 'boilerplate.components.Footer.copyright.message',
    defaultMessage: '© 2018 TutorFind. All rights reserved.',
  },
  authorMessage: {
    id: 'boilerplate.components.Footer.author.message',
    defaultMessage: `
      Made with love by {author}.
    `,
  },
});
