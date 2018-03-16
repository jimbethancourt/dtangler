// This product is provided under the terms of EPL (Eclipse Public License) 
// version 2.0.
//
// The full license text can be read from: https://www.eclipse.org/legal/epl-2.0/

package org.hjug.dtangler.core.util;

import java.util.regex.Pattern;

public class WildcardMatch {
	private String regex;

	public WildcardMatch(String mask) {
		super();
		this.regex = createRegex(mask);
	}

	public boolean isMatch(String value) {
		return regex.contains("*") && value.matches(regex);
	}

	private String createRegex(String mask) {
		int pos = 0;
		int nextPos = mask.indexOf("*", pos);

		StringBuilder sb = new StringBuilder();
		while (nextPos >= 0) {
			sb.append(Pattern.quote(mask.substring(pos, nextPos)));
			sb.append(".*");
			pos = nextPos + 1;
			nextPos = pos < mask.length() ? mask.indexOf("*", pos) : -1;
		}
		sb.append(Pattern.quote(mask.substring(pos, mask.length())));
		return sb.toString();
	}
}