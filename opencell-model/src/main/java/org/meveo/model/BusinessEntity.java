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
package org.meveo.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public class BusinessEntity extends EnableEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "CODE", nullable = false, length = 255)
    // TODO : Create sql script to ad index. @Index(name = "CODE_IDX")
    @Size(max = 255, min = 1)
    @NotNull
    protected String code;

    @Column(name = "DESCRIPTION", nullable = true, length = 255)
    @Size(max = 255)
    protected String description;

    @Transient
    protected boolean appendGeneratedCode = false;

    public String getCode() {    	
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the appendGeneratedCode
     */
    public boolean isAppendGeneratedCode() {
        return appendGeneratedCode;
    }

    /**
     * @param appendGeneratedCode the appendGeneratedCode to set
     */
    public void setAppendGeneratedCode(boolean appendGeneratedCode) {
        this.appendGeneratedCode = appendGeneratedCode;
    }

    public String getDescriptionOrCode() {
        if (!StringUtils.isBlank(description)) {
            return description;
        } else {
            return code;
        }
    }
    
	/**
	 * This method can be overridden to allow child entities to identify their
	 * parent entity.
	 * 
	 * @return The parent entity.
	 */
	public BusinessEntity getParentEntity() {
		return null;
	}
	
	/**
	 * This method can be overridden to allow child entities to identify their
	 * parent entity's type.
	 * 
	 * @return The parent entity's type.
	 */
	public Class<? extends BusinessEntity> getParentEntityType() {
		if(getParentEntity() != null){
			return getParentEntity().getClass();
		}
		return null;
	}
 
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime * 1; //super.hashCode();
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (!(obj instanceof BusinessEntity)) { // Fails with proxed objects: getClass() != obj.getClass()){
            return false;
        }

        BusinessEntity other = (BusinessEntity) obj;

        if (id != null && other.getId() != null && id.equals(other.getId())) {
             return true;
        }
        if (code == null) {
            if (other.getCode() != null) {
                return false;
            }
        } else if (!code.equals(other.getCode())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s[%s, code=%s]", this.getClass().getName(), super.toString(), code);
    }
    
    public void setDescriptionOrCode(String val) {
    	setDescription(val);
    }
    
}