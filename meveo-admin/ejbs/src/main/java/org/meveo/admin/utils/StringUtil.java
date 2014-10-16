/*
 * (C) Copyright 2009-2014 Manaty SARL (http://manaty.net/) and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.meveo.admin.utils;

import javax.inject.Named;

import org.meveo.commons.utils.StringUtils;

/**
 * @author Tyshan(tyshan@manaty.net)
 */

@Named
public class StringUtil {

	public String merge(String... s) {
		StringBuilder sb = new StringBuilder();
		if (!StringUtils.isBlank(s)) {
			for (String temp : s) {
				if (s == null || s.equals(""))
					continue;
				sb.append(temp);
			}
		}
		return sb.toString();
	}
}
