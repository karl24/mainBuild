/*
 *
 * LanguageProvider actions
 *
 */

import {
  CHANGE_ACCOUNT,
} from './constants';

export function changeToggle(languageLocale) {
  return {
    type: CHANGE_ACCOUNT,
    locale: languageLocale,
  };
}
