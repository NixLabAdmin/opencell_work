package org.meveo.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.meveo.api.dto.account.BankCoordinatesDto;
import org.meveo.api.dto.invoice.InvoiceConfigurationDto;
import org.meveo.model.crm.Provider;

/**
 * @author Edward P. Legaspi
 **/
@XmlRootElement(name = "Provider")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDto extends BaseDto {

	private static final long serialVersionUID = 5599223889050605880L;

	@XmlAttribute(required = true)
	private String code;

	private String description;
	private String currency;
	private String country;
	private String language;
	private boolean multiCurrency;
	private boolean multiCountry;
	private boolean multiLanguage;
	private String userAccount;

	private boolean enterprise;
	private boolean levelDuplication;	
	private boolean displayFreeTransacInInvoice;
	private Integer rounding = 2;
	private Long prepaidReservationExpirationDelayinMillisec;
	private String discountAccountingCode;
	private String email;
	private BankCoordinatesDto bankCoordinates = new BankCoordinatesDto();
	private boolean recognizeRevenue;
	private InvoiceConfigurationDto invoiceConfiguration = new InvoiceConfigurationDto();

	@XmlElement(required = false)
	private CustomFieldsDto customFields;

	public ProviderDto() {

	}
	
	public ProviderDto(Provider e, CustomFieldsDto customFieldInstances) {
		this(e, customFieldInstances, true);
	}

	public ProviderDto(Provider e, CustomFieldsDto customFieldInstances, boolean loadProviderData) {
		code = e.getCode();
		
		if (loadProviderData) {			
			description = e.getDescription();			
			if (e.getCurrency() != null) {
				currency = e.getCurrency().getCurrencyCode();
			}
			if (e.getCountry() != null) {
				country = e.getCountry().getCountryCode();
			}
			if (e.getLanguage() != null) {
				language = e.getLanguage().getLanguageCode();
			}
			multiCurrency = e.getMulticurrencyFlag();
			multiCountry = e.getMulticountryFlag();
			multiLanguage = e.getMultilanguageFlag();
			rounding = e.getRounding();
			prepaidReservationExpirationDelayinMillisec = e.getPrepaidReservationExpirationDelayinMillisec();
			discountAccountingCode = e.getDiscountAccountingCode();
			email = e.getEmail();			

			this.setEnterprise(e.isEntreprise());
			this.setLevelDuplication(e.isLevelDuplication());
			
			this.setDisplayFreeTransacInInvoice(e.isDisplayFreeTransacInInvoice());
			this.setRecognizeRevenue(e.isRecognizeRevenue());

			if (e.getBankCoordinates() != null) {
				this.setBankCoordinates(new BankCoordinatesDto(e.getBankCoordinates()));
			}

			if (e.getInvoiceConfiguration() != null) {
				this.setInvoiceConfiguration(new InvoiceConfigurationDto(e.getInvoiceConfiguration()));
			}
		}
		
		customFields = customFieldInstances;
	}

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public boolean isMultiCurrency() {
		return multiCurrency;
	}

	public void setMultiCurrency(boolean multiCurrency) {
		this.multiCurrency = multiCurrency;
	}

	public boolean isMultiCountry() {
		return multiCountry;
	}

	public void setMultiCountry(boolean multiCountry) {
		this.multiCountry = multiCountry;
	}

	public boolean isMultiLanguage() {
		return multiLanguage;
	}

	public void setMultiLanguage(boolean multiLanguage) {
		this.multiLanguage = multiLanguage;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public CustomFieldsDto getCustomFields() {
		return customFields;
	}

	public void setCustomFields(CustomFieldsDto customFields) {
		this.customFields = customFields;
	}


	public boolean isEnterprise() {
		return enterprise;
	}

	public void setEnterprise(boolean enterprise) {
		this.enterprise = enterprise;
	}

	public boolean isLevelDuplication() {
		return levelDuplication;
	}

	public void setLevelDuplication(boolean levelDuplication) {
		this.levelDuplication = levelDuplication;
	}

	

	public boolean isDisplayFreeTransacInInvoice() {
		return displayFreeTransacInInvoice;
	}

	public void setDisplayFreeTransacInInvoice(boolean displayFreeTransacInInvoice) {
		this.displayFreeTransacInInvoice = displayFreeTransacInInvoice;
	}	

	public BankCoordinatesDto getBankCoordinates() {
		return bankCoordinates;
	}

	public void setBankCoordinates(BankCoordinatesDto bankCoordinates) {
		this.bankCoordinates = bankCoordinates;
	}

	public InvoiceConfigurationDto getInvoiceConfiguration() {
		return invoiceConfiguration;
	}

	public void setInvoiceConfiguration(InvoiceConfigurationDto invoiceConfiguration) {
		this.invoiceConfiguration = invoiceConfiguration;
	}

	public Integer getRounding() {
		return rounding;
	}

	public void setRounding(Integer rounding) {
		this.rounding = rounding;
	}

	public Long getPrepaidReservationExpirationDelayinMillisec() {
		return prepaidReservationExpirationDelayinMillisec;
	}

	public void setPrepaidReservationExpirationDelayinMillisec(Long prepaidReservationExpirationDelayinMillisec) {
		this.prepaidReservationExpirationDelayinMillisec = prepaidReservationExpirationDelayinMillisec;
	}

	public String getDiscountAccountingCode() {
		return discountAccountingCode;
	}

	public void setDiscountAccountingCode(String discountAccountingCode) {
		this.discountAccountingCode = discountAccountingCode;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isRecognizeRevenue() {
		return recognizeRevenue;
	}

	public void setRecognizeRevenue(boolean recognizeRevenue) {
		this.recognizeRevenue = recognizeRevenue;
	}

	@Override
	public String toString() {
		return "ProviderDto [code=" + code + ", description=" + description + ", currency=" + currency + ", country=" + country + ", language=" + language + ", multiCurrency=" + multiCurrency + ", multiCountry=" + multiCountry + ", multiLanguage=" + multiLanguage + ", userAccount=" + userAccount + ", enterprise=" + enterprise + ", levelDuplication=" + levelDuplication + ", displayFreeTransacInInvoice=" + displayFreeTransacInInvoice + ", rounding=" + rounding
				+ ", prepaidReservationExpirationDelayinMillisec=" + prepaidReservationExpirationDelayinMillisec + ", discountAccountingCode=" + discountAccountingCode + ", email=" + email + ", bankCoordinates=" + bankCoordinates + ", recognizeRevenue=" + recognizeRevenue + ", invoiceConfiguration=" + invoiceConfiguration + ", customFields=" + customFields + "]";
	}


	

}
