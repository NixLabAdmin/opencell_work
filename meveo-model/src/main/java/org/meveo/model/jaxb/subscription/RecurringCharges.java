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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.02.03 at 11:45:33 PM WET 
//


package org.meveo.model.jaxb.subscription;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}amountWithoutTax"/>
 *         &lt;element ref="{}amountWithTax"/>
 *         &lt;element ref="{}C1"/>
 *         &lt;element ref="{}C2"/>
 *         &lt;element ref="{}C3"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "amountWithoutTax",
    "amountWithTax",
    "c1",
    "c2",
    "c3"
})
@XmlRootElement(name = "recurringCharges")
public class RecurringCharges {

    @XmlElement(required = true)
    protected String amountWithoutTax;
    @XmlElement(required = true)
    protected String amountWithTax;
    @XmlElement(name = "C1", required = true)
    protected String c1;
    @XmlElement(name = "C2", required = true)
    protected String c2;
    @XmlElement(name = "C3", required = true)
    protected String c3;

    /**
     * Gets the value of the amountWithoutTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountWithoutTax() {
        return amountWithoutTax;
    }

    /**
     * Sets the value of the amountWithoutTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountWithoutTax(String value) {
        this.amountWithoutTax = value;
    }

    /**
     * Gets the value of the amountWithTax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountWithTax() {
        return amountWithTax;
    }

    /**
     * Sets the value of the amountWithTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountWithTax(String value) {
        this.amountWithTax = value;
    }

    /**
     * Gets the value of the c1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC1() {
        return c1;
    }

    /**
     * Sets the value of the c1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC1(String value) {
        this.c1 = value;
    }

    /**
     * Gets the value of the c2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC2() {
        return c2;
    }

    /**
     * Sets the value of the c2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC2(String value) {
        this.c2 = value;
    }

    /**
     * Gets the value of the c3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getC3() {
        return c3;
    }

    /**
     * Sets the value of the c3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setC3(String value) {
        this.c3 = value;
    }

}
