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
package org.meveo.model.wf;

public enum TransitionRuleTypeEnum {

    STRING(1, "TransitionRuleTypeEnum.string"),
    DATE(2, "TransitionRuleTypeEnum.date"),
    NUMBER(3, "TransitionRuleTypeEnum.number"),
    RANGE_STRING(4, "TransitionRuleTypeEnum.range.string"),
    RANGE_DATE(5, "TransitionRuleTypeEnum.range.date"),
    RANGE_NUMBER(6, "TransitionRuleTypeEnum.range.number");

    private Integer id;
    private String label;

    private TransitionRuleTypeEnum(Integer id, String label) {
        this.id = id;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public static TransitionRuleTypeEnum getValue(Integer id) {
        if (id != null) {
            for (TransitionRuleTypeEnum status : values()) {
                if (id.equals(status.getId())) {
                    return status;
                }
            }
        }
        return null;
    }
}
