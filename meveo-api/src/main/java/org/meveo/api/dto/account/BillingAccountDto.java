package org.meveo.api.dto.account;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.meveo.model.AccountEntity;
import org.meveo.model.billing.BillingAccount;

/**
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "BillingAccount")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillingAccountDto extends AccountDto {

	private static final long serialVersionUID = 8701417481481359155L;

	@XmlAttribute(required = true)
	private String customerAccount;

	@XmlAttribute(required = true)
	private String billingCycle;

	@XmlAttribute(required = true)
	private String country;

	@XmlAttribute(required = true)
	private String language;

	@XmlAttribute(required = true)
	private String paymentMethod;

	private Date nextInvoiceDate;
	private Date subscriptionDate;
	private Date terminationDate;
	private String paymentTerms;
	private Boolean electronicBilling;

	public BillingAccountDto() {

	}

	public BillingAccountDto(BillingAccount e) {
		super((AccountEntity) e);

		if (e.getCustomerAccount() != null) {
			customerAccount = e.getCustomerAccount().getCode();
		}
		if (e.getBillingCycle() != null) {
			billingCycle = e.getBillingCycle().getCode();
		}
		if (e.getTradingCountry() != null) {
			country = e.getTradingCountry().getCountryCode();
		}
		if (e.getTradingLanguage() != null) {
			language = e.getTradingLanguage().getLanguageCode();
		}
		nextInvoiceDate = e.getNextInvoiceDate();
		subscriptionDate = e.getSubscriptionDate();
		terminationDate = e.getTerminationDate();
		electronicBilling = e.getElectronicBilling();
		if (e.getPaymentMethod() != null) {
			paymentMethod = e.getPaymentMethod().name();
		}
		if (e.getPaymentTerm() != null) {
			paymentTerms = e.getPaymentTerm().name();
		}

	}

	public String getCustomerAccount() {
		return customerAccount;
	}

	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getNextInvoiceDate() {
		return nextInvoiceDate;
	}

	public void setNextInvoiceDate(Date nextInvoiceDate) {
		this.nextInvoiceDate = nextInvoiceDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Boolean getElectronicBilling() {
		return electronicBilling;
	}

	public void setElectronicBilling(Boolean electronicBilling) {
		this.electronicBilling = electronicBilling;
	}

	@Override
	public String toString() {
		return "BillingAccountDto [customerAccount=" + customerAccount + ", billingCycle=" + billingCycle
				+ ", country=" + country + ", language=" + language + ", paymentMethod=" + paymentMethod
				+ ", nextInvoiceDate=" + nextInvoiceDate + ", subscriptionDate=" + subscriptionDate
				+ ", terminationDate=" + terminationDate + ", paymentTerms=" + paymentTerms + ", electronicBilling="
				+ electronicBilling + ", toString()=" + super.toString() + "]";
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

}