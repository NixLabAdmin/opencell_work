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
// Generated on: 2011.02.02 at 02:29:16 PM WET 
//

package org.meveo.model.jaxb.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tradingCurrencyCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tradingCountryCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="tradingLanguageCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;sequence>
 *         &lt;element ref="{}customers"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {  "code",
		"description",
		"tradingCurrencyCode",
		"tradingCountryCode",
		"tradingLanguageCode",
		"customers" })
@XmlRootElement(name = "seller")
public class Seller {

    @XmlAttribute(name = "code")
    protected String code;
    @XmlAttribute(name = "description")
    protected String description;
    @XmlAttribute(name = "tradingCurrencyCode")
    protected String tradingCurrencyCode;
    @XmlAttribute(name = "tradingCountryCode")
    protected String tradingCountryCode;
    @XmlAttribute(name = "tradingLanguageCode")
    protected String tradingLanguageCode;
    
    protected Customers customers;

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

	public String getTradingCurrencyCode() {
		return tradingCurrencyCode;
	}

	public void setTradingCurrencyCode(String tradingCurrencyCode) {
		this.tradingCurrencyCode = tradingCurrencyCode;
	}

	public String getTradingCountryCode() {
		return tradingCountryCode;
	}

	public void setTradingCountryCode(String tradingCountryCode) {
		this.tradingCountryCode = tradingCountryCode;
	}

	public String getTradingLanguageCode() {
		return tradingLanguageCode;
	}

	public void setTradingLanguageCode(String tradingLanguageCode) {
		this.tradingLanguageCode = tradingLanguageCode;
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

}
