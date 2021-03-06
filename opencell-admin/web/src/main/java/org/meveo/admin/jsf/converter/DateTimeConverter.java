/*
 * (C) Copyright 2015-2016 Opencell SAS (http://opencellsoft.com/) and contributors.
 * (C) Copyright 2009-2014 Manaty SARL (http://manaty.net/) and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * This program is not suitable for any direct or indirect application in MILITARY industry
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.meveo.admin.jsf.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.meveo.commons.utils.ParamBean;

@FacesConverter("dateTimeConverter")
public class DateTimeConverter implements Converter {


    private ParamBean paramBean=ParamBean.getInstance();

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent uIComponent, String str) {
		String dateFormat = paramBean.getProperty("meveo.dateTimeFormat", "dd/MM/yyyy HH:mm");
		DateFormat df = new SimpleDateFormat(dateFormat, FacesContext.getCurrentInstance()
				.getViewRoot().getLocale());

		try {
			return df.parse(str);
		} catch (ParseException e) {
			return "";
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uIComponent, Object obj) {
		String dateFormat = paramBean.getProperty("meveo.dateTimeFormat", "dd/MM/yyyy HH:mm");
		DateFormat df = new SimpleDateFormat(dateFormat, FacesContext.getCurrentInstance()
				.getViewRoot().getLocale());

		return df.format(obj);
	}

}
