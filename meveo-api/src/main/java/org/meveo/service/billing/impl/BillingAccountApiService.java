package org.meveo.service.billing.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.meveo.admin.exception.DuplicateDefaultAccountException;
import org.meveo.api.MeveoApiErrorCode;
import org.meveo.api.dto.account.BillingAccountDto;
import org.meveo.api.dto.account.BillingAccountsDto;
import org.meveo.api.dto.invoice.InvoiceDto;
import org.meveo.api.exception.DeleteReferencedEntityException;
import org.meveo.api.exception.EntityAlreadyExistsException;
import org.meveo.api.exception.EntityDoesNotExistsException;
import org.meveo.api.exception.MeveoApiException;
import org.meveo.api.exception.MissingParameterException;
import org.meveo.commons.utils.StringUtils;
import org.meveo.model.admin.User;
import org.meveo.model.billing.BankCoordinates;
import org.meveo.model.billing.BillingAccount;
import org.meveo.model.billing.BillingCycle;
import org.meveo.model.billing.Invoice;
import org.meveo.model.billing.InvoiceTypeEnum;
import org.meveo.model.billing.TradingCountry;
import org.meveo.model.billing.TradingLanguage;
import org.meveo.model.crm.Provider;
import org.meveo.model.payments.CustomerAccount;
import org.meveo.model.payments.PaymentMethodEnum;
import org.meveo.model.payments.PaymentTermEnum;
import org.meveo.service.crm.impl.AccountApiService;
import org.meveo.service.payments.impl.CustomerAccountService;

/**
 * @author Edward P. Legaspi
 **/
@Stateless
public class BillingAccountApiService extends AccountApiService {

	@Inject
	private BillingAccountService billingAccountService;

	@Inject
	private BillingCycleService billingCycleService;

	@Inject
	private TradingCountryService tradingCountryService;

	@Inject
	private TradingLanguageService tradingLanguageService;

	@Inject
	private CustomerAccountService customerAccountService;
    
	public void create(BillingAccountDto postData, User currentUser)
			throws MeveoApiException, DuplicateDefaultAccountException {
		create(postData, currentUser, true);
	}

	public void create(BillingAccountDto postData, User currentUser,
			boolean checkCustomFields) throws MeveoApiException,
			DuplicateDefaultAccountException {
		if (!StringUtils.isBlank(postData.getCode())
				&& !StringUtils.isBlank(postData.getDescription())
				&& !StringUtils.isBlank(postData.getCustomerAccount())
				&& !StringUtils.isBlank(postData.getBillingCycle())
				&& !StringUtils.isBlank(postData.getCountry())
				&& !StringUtils.isBlank(postData.getLanguage())
				&& !StringUtils.isBlank(postData.getPaymentMethod())) {
			Provider provider = currentUser.getProvider();

			if (billingAccountService.findByCode(postData.getCode(), provider) != null) {
				throw new EntityAlreadyExistsException(BillingAccount.class,
						postData.getCode());
			}

			CustomerAccount customerAccount = customerAccountService
					.findByCode(postData.getCustomerAccount(), provider);
			if (customerAccount == null) {
				throw new EntityDoesNotExistsException(CustomerAccount.class,
						postData.getCustomerAccount());
			}

			BillingCycle billingCycle = billingCycleService
					.findByBillingCycleCode(postData.getBillingCycle(),
							provider);
			if (billingCycle == null) {
				throw new EntityDoesNotExistsException(BillingCycle.class,
						postData.getBillingCycle());
			}

			TradingCountry tradingCountry = tradingCountryService
					.findByTradingCountryCode(postData.getCountry(), provider);
			if (tradingCountry == null) {
				throw new EntityDoesNotExistsException(TradingCountry.class,
						postData.getCountry());
			}

			TradingLanguage tradingLanguage = tradingLanguageService
					.findByTradingLanguageCode(postData.getLanguage(), provider);
			if (tradingLanguage == null) {
				throw new EntityDoesNotExistsException(TradingLanguage.class,
						postData.getLanguage());
			}

			PaymentMethodEnum paymentMethod = null;

			try {
				paymentMethod = PaymentMethodEnum.valueOf(postData
						.getPaymentMethod());
			} catch (IllegalArgumentException e) {
				throw new MeveoApiException(
						MeveoApiErrorCode.BUSINESS_API_EXCEPTION,
						"Invalid payment method=" + postData.getPaymentMethod());
			}

			BillingAccount billingAccount = new BillingAccount();
			populate(postData, billingAccount, currentUser);

			billingAccount.setCustomerAccount(customerAccount);
			billingAccount.setBillingCycle(billingCycle);
			billingAccount.setTradingCountry(tradingCountry);
			billingAccount.setTradingLanguage(tradingLanguage);
			billingAccount.setPaymentMethod(paymentMethod);
			if (!StringUtils.isBlank(postData.getPaymentTerms())) {
				try {
					billingAccount.setPaymentTerm(PaymentTermEnum
							.valueOf(postData.getPaymentTerms()));
				} catch (IllegalArgumentException e) {
					log.error("InvalidEnum for paymentTerm with name={}",
							postData.getPaymentTerms());
					throw new MeveoApiException(
							MeveoApiErrorCode.INVALID_ENUM_VALUE,
							"Enum for PaymentTerm with name="
									+ postData.getPaymentTerms()
									+ " does not exists.");
				}
			}
			billingAccount.setNextInvoiceDate(postData.getNextInvoiceDate());
			billingAccount.setSubscriptionDate(postData.getSubscriptionDate());
			billingAccount.setTerminationDate(postData.getTerminationDate());
			if (postData.getElectronicBilling() == null) {
				billingAccount.setElectronicBilling(false);
			} else {
				billingAccount.setElectronicBilling(postData
						.getElectronicBilling());
			}
			billingAccount.setEmail(postData.getEmail());
			billingAccount.setExternalRef1(postData.getExternalRef1());
			billingAccount.setExternalRef2(postData.getExternalRef2());

			if (postData.getBankCoordinates() != null) {
				billingAccount.getBankCoordinates().setBankCode(
						postData.getBankCoordinates().getBankCode());
				billingAccount.getBankCoordinates().setBranchCode(
						postData.getBankCoordinates().getBranchCode());
				billingAccount.getBankCoordinates().setAccountNumber(
						postData.getBankCoordinates().getAccountNumber());
				billingAccount.getBankCoordinates().setKey(
						postData.getBankCoordinates().getKey());
				billingAccount.getBankCoordinates().setIban(
						postData.getBankCoordinates().getIban());
				billingAccount.getBankCoordinates().setBic(
						postData.getBankCoordinates().getBic());
				billingAccount.getBankCoordinates().setAccountOwner(
						postData.getBankCoordinates().getAccountOwner());
				billingAccount.getBankCoordinates().setBankName(
						postData.getBankCoordinates().getBankName());
				billingAccount.getBankCoordinates().setBankId(
						postData.getBankCoordinates().getBankId());
				billingAccount.getBankCoordinates().setIssuerNumber(
						postData.getBankCoordinates().getIssuerNumber());
				billingAccount.getBankCoordinates().setIssuerName(
						postData.getBankCoordinates().getIssuerName());
				billingAccount.getBankCoordinates().setIcs(
						postData.getBankCoordinates().getIcs());
			}
						
			billingAccountService.createBillingAccount(billingAccount,
					currentUser, provider);			

	        // Validate and populate customFields
	        try {
	            populateCustomFields(postData.getCustomFields(), billingAccount, true, currentUser, checkCustomFields);
	        } catch (IllegalArgumentException | IllegalAccessException e) {
	            log.error("Failed to associate custom field instance to an entity", e);
	            throw new MeveoApiException("Failed to associate custom field instance to an entity");
	        }
	        
		} else {
			if (StringUtils.isBlank(postData.getCode())) {
				missingParameters.add("code");
			}
			if (StringUtils.isBlank(postData.getDescription())) {
				missingParameters.add("description");
			}
			if (StringUtils.isBlank(postData.getCustomerAccount())) {
				missingParameters.add("customerAccount");
			}
			if (StringUtils.isBlank(postData.getBillingCycle())) {
				missingParameters.add("billingCycle");
			}
			if (StringUtils.isBlank(postData.getCountry())) {
				missingParameters.add("country");
			}
			if (StringUtils.isBlank(postData.getLanguage())) {
				missingParameters.add("language");
			}
			if (StringUtils.isBlank(postData.getPaymentMethod())) {
				missingParameters.add("paymentMethod");
			}

			throw new MissingParameterException(
					getMissingParametersExceptionMessage());
		}
	}

	public void update(BillingAccountDto postData, User currentUser)
			throws MeveoApiException, DuplicateDefaultAccountException {
		update(postData, currentUser, true);
	}

	public void update(BillingAccountDto postData, User currentUser,
			boolean checkCustomFields) throws MeveoApiException,
			DuplicateDefaultAccountException {
		if (!StringUtils.isBlank(postData.getCode())
				&& !StringUtils.isBlank(postData.getDescription())
				&& !StringUtils.isBlank(postData.getCustomerAccount())
				&& !StringUtils.isBlank(postData.getBillingCycle())
				&& !StringUtils.isBlank(postData.getCountry())
				&& !StringUtils.isBlank(postData.getLanguage())
				&& !StringUtils.isBlank(postData.getPaymentMethod())) {
			Provider provider = currentUser.getProvider();

			BillingAccount billingAccount = billingAccountService.findByCode(
					postData.getCode(), provider);
			if (billingAccount == null) {
				throw new EntityDoesNotExistsException(BillingAccount.class,
						postData.getCode());
			}

			if (!StringUtils.isBlank(postData.getCustomerAccount())) {
				CustomerAccount customerAccount = customerAccountService
						.findByCode(postData.getCustomerAccount(), provider);
				if (customerAccount == null) {
					throw new EntityDoesNotExistsException(
							CustomerAccount.class,
							postData.getCustomerAccount());
				}
				billingAccount.setCustomerAccount(customerAccount);
			}

			if (!StringUtils.isBlank(postData.getBillingCycle())) {
				BillingCycle billingCycle = billingCycleService
						.findByBillingCycleCode(postData.getBillingCycle(),
								provider);
				if (billingCycle == null) {
					throw new EntityDoesNotExistsException(BillingCycle.class,
							postData.getBillingCycle());
				}
				billingAccount.setBillingCycle(billingCycle);
			}

			if (!StringUtils.isBlank(postData.getCountry())) {
				TradingCountry tradingCountry = tradingCountryService
						.findByTradingCountryCode(postData.getCountry(),
								provider);
				if (tradingCountry == null) {
					throw new EntityDoesNotExistsException(
							TradingCountry.class, postData.getCountry());
				}
				billingAccount.setTradingCountry(tradingCountry);
			}

			if (!StringUtils.isBlank(postData.getLanguage())) {
				TradingLanguage tradingLanguage = tradingLanguageService
						.findByTradingLanguageCode(postData.getLanguage(),
								provider);
				if (tradingLanguage == null) {
					throw new EntityDoesNotExistsException(
							TradingLanguage.class, postData.getLanguage());
				}
				billingAccount.setTradingLanguage(tradingLanguage);
			}

			if (!StringUtils.isBlank(postData.getPaymentMethod())) {
				PaymentMethodEnum paymentMethod = null;

				try {
					paymentMethod = PaymentMethodEnum.valueOf(postData
							.getPaymentMethod());
				} catch (IllegalArgumentException e) {
					log.error("InvalidEnum for paymentMethod with name={}",
							postData.getPaymentMethod());
					throw new MeveoApiException(
							MeveoApiErrorCode.INVALID_ENUM_VALUE,
							"Enum for PaymentMethod with name="
									+ postData.getPaymentMethod()
									+ " does not exists.");
				}

				billingAccount.setPaymentMethod(paymentMethod);
			}

			if (!StringUtils.isBlank(postData.getPaymentTerms())) {
				try {
					billingAccount.setPaymentTerm(PaymentTermEnum
							.valueOf(postData.getPaymentTerms()));
				} catch (IllegalArgumentException e) {
					log.warn("InvalidEnum for paymentTerm with name={}",
							postData.getPaymentTerms());
				}
			}
			
			if (!StringUtils.isBlank(postData.getExternalRef1())) {
				billingAccount.setExternalRef1(postData.getExternalRef1());
			}
			if (!StringUtils.isBlank(postData.getExternalRef2())) {
				billingAccount.setExternalRef2(postData.getExternalRef2());
			}
			
			updateAccount(billingAccount, postData, currentUser, checkCustomFields);

			if (!StringUtils.isBlank(postData.getNextInvoiceDate())) {
				billingAccount
						.setNextInvoiceDate(postData.getNextInvoiceDate());
			}
			if (!StringUtils.isBlank(postData.getSubscriptionDate())) {
				billingAccount.setSubscriptionDate(postData
						.getSubscriptionDate());
			}
			if (!StringUtils.isBlank(postData.getTerminationDate())) {
				billingAccount
						.setTerminationDate(postData.getTerminationDate());
			}
			if (!StringUtils.isBlank(postData.getElectronicBilling())) {
				billingAccount.setElectronicBilling(postData
						.getElectronicBilling());
			}
			if (!StringUtils.isBlank(postData.getEmail())) {
				billingAccount.setEmail(postData.getEmail());
			}
			if (postData.getBankCoordinates() != null) {
				BankCoordinates bankCoordinates = new BankCoordinates();
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getBankCode())) {
					bankCoordinates.setBankCode(postData.getBankCoordinates()
							.getBankCode());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getBranchCode())) {
					bankCoordinates.setBranchCode(postData.getBankCoordinates()
							.getBranchCode());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getAccountNumber())) {
					bankCoordinates.setAccountNumber(postData
							.getBankCoordinates().getAccountNumber());
				}
				if (!StringUtils
						.isBlank(postData.getBankCoordinates().getKey())) {
					bankCoordinates.setKey(postData.getBankCoordinates()
							.getKey());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getIban())) {
					bankCoordinates.setIban(postData.getBankCoordinates()
							.getIban());
				}
				if (!StringUtils
						.isBlank(postData.getBankCoordinates().getBic())) {
					bankCoordinates.setBic(postData.getBankCoordinates()
							.getBic());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getAccountOwner())) {
					bankCoordinates.setAccountOwner(postData
							.getBankCoordinates().getAccountOwner());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getBankName())) {
					bankCoordinates.setBankName(postData.getBankCoordinates()
							.getBankName());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getBankId())) {
					bankCoordinates.setBankId(postData.getBankCoordinates()
							.getBankId());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getIssuerNumber())) {
					bankCoordinates.setIssuerNumber(postData
							.getBankCoordinates().getIssuerNumber());
				}
				if (!StringUtils.isBlank(postData.getBankCoordinates()
						.getIssuerName())) {
					bankCoordinates.setIssuerName(postData.getBankCoordinates()
							.getIssuerName());
				}
				if (!StringUtils
						.isBlank(postData.getBankCoordinates().getIcs())) {
					bankCoordinates.setIcs(postData.getBankCoordinates()
							.getIcs());
				}
				billingAccount.setBankCoordinates(bankCoordinates);
			}

			billingAccountService.updateAudit(billingAccount, currentUser);			

	        // Validate and populate customFields
	        try {
	            populateCustomFields(postData.getCustomFields(), billingAccount, false, currentUser, checkCustomFields);
	        } catch (IllegalArgumentException | IllegalAccessException e) {
	            log.error("Failed to associate custom field instance to an entity", e);
	            throw new MeveoApiException("Failed to associate custom field instance to an entity");
	        }
			
		} else {
			if (StringUtils.isBlank(postData.getCode())) {
				missingParameters.add("code");
			}
			if (StringUtils.isBlank(postData.getDescription())) {
				missingParameters.add("description");
			}
			if (StringUtils.isBlank(postData.getCustomerAccount())) {
				missingParameters.add("customerAccount");
			}
			if (StringUtils.isBlank(postData.getBillingCycle())) {
				missingParameters.add("billingCycle");
			}
			if (StringUtils.isBlank(postData.getCountry())) {
				missingParameters.add("country");
			}
			if (StringUtils.isBlank(postData.getLanguage())) {
				missingParameters.add("language");
			}
			if (StringUtils.isBlank(postData.getPaymentMethod())) {
				missingParameters.add("paymentMethod");
			}

			throw new MissingParameterException(
					getMissingParametersExceptionMessage());
		}
	}

	public BillingAccountDto find(String billingAccountCode, Provider provider)
			throws MeveoApiException {
		if (!StringUtils.isBlank(billingAccountCode)) {
			BillingAccount billingAccount = billingAccountService.findByCode(
					billingAccountCode, provider);
			if (billingAccount == null) {
				throw new EntityDoesNotExistsException(BillingAccount.class,
						billingAccountCode);
			}

			return accountHierarchyApiService.billingAccountToDto(billingAccount);
		} else {
			missingParameters.add("billingAccountCode");

			throw new MissingParameterException(
					getMissingParametersExceptionMessage());
		}
	}

	public void remove(String billingAccountCode, Provider provider)throws MeveoApiException {		
		if (StringUtils.isBlank(billingAccountCode)) {
			missingParameters.add("billingAccountCode");
			throw new MissingParameterException(getMissingParametersExceptionMessage());
		}				
		BillingAccount billingAccount = billingAccountService.findByCode(billingAccountCode, provider);
		if (billingAccount == null) {
			throw new EntityDoesNotExistsException(BillingAccount.class,billingAccountCode);
		}
		try{			
			billingAccountService.remove(billingAccount);
			billingAccountService.commit();
		}catch(Exception e){			
			if(e.getMessage().indexOf("ConstraintViolationException") > -1){
				throw new DeleteReferencedEntityException(BillingAccount.class,billingAccountCode);
			}
			throw new MeveoApiException(MeveoApiErrorCode.BUSINESS_API_EXCEPTION,"Cannot delete entity");			
		}			
	}

	public BillingAccountsDto listByCustomerAccount(String customerAccountCode,
			Provider provider) throws MeveoApiException {
		if (!StringUtils.isBlank(customerAccountCode)) {
			CustomerAccount customerAccount = customerAccountService
					.findByCode(customerAccountCode, provider);
			if (customerAccount == null) {
				throw new EntityDoesNotExistsException(CustomerAccount.class,
						customerAccountCode);
			}

			BillingAccountsDto result = new BillingAccountsDto();
			List<BillingAccount> billingAccounts = billingAccountService
					.listByCustomerAccount(customerAccount);
			if (billingAccounts != null) {
				for (BillingAccount ba : billingAccounts) {
					BillingAccountDto billingAccountDto = accountHierarchyApiService.billingAccountToDto(ba);
					
					List<Invoice> invoices = ba.getInvoices();
					if (invoices != null && invoices.size() > 0) {
						List<InvoiceDto> invoicesDto = new ArrayList<InvoiceDto>();
						String billingAccountCode = ba.getCode();
						if (invoices != null && invoices.size() > 0) {
							for (Invoice i: invoices) {
								if (i.getInvoiceTypeEnum() == InvoiceTypeEnum.CREDIT_NOTE_ADJUST) {
									InvoiceDto invoiceDto = new InvoiceDto(i, billingAccountCode);
									invoicesDto.add(invoiceDto);
								}
							}
							billingAccountDto.setInvoices(invoicesDto);
						}
					}
					
					
					result.getBillingAccount().add(billingAccountDto);
				}
			}

			return result;
		} else {
			missingParameters.add("customerAccountCode");

			throw new MissingParameterException(
					getMissingParametersExceptionMessage());
		}
	}

	/**
	 * Create or update Billing Account based on Billing Account Code
	 * 
	 * @param postData
	 * @param currentUser
	 * @throws MeveoApiException
	 * @throws DuplicateDefaultAccountException
	 */
	public void createOrUpdate(BillingAccountDto postData, User currentUser)
			throws MeveoApiException, DuplicateDefaultAccountException {
		if (billingAccountService.findByCode(postData.getCode(),
				currentUser.getProvider()) == null) {
			create(postData, currentUser);
		} else {
			update(postData, currentUser);
		}
	}
}