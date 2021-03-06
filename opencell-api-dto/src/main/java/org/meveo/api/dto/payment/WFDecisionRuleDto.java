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
package org.meveo.api.dto.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.meveo.api.dto.BaseDto;
import org.meveo.model.wf.WFDecisionRule;

@XmlType(name = "WFDecisionRule")
@XmlAccessorType(XmlAccessType.FIELD)
public class WFDecisionRuleDto extends BaseDto {
	private static final long serialVersionUID = 8309866046667741458L;

	@XmlElement(required = true)
	private String name;

    @XmlElement(required = true)
    private String value;

	public WFDecisionRuleDto(){
	}

	public WFDecisionRuleDto(WFDecisionRule wfDecisionRule) {
		this.name = wfDecisionRule.getName();
        this.value = wfDecisionRule.getValue();
	}
	
	public WFDecisionRule fromDto(WFDecisionRule wfDecisionRule) {
		if(wfDecisionRule == null)
            wfDecisionRule = new WFDecisionRule();
        wfDecisionRule.setName(getName());
        wfDecisionRule.setValue(getValue());
		return wfDecisionRule;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

