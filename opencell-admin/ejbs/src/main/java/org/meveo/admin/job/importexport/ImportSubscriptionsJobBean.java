package org.meveo.admin.job.importexport;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.xml.bind.JAXBException;

import org.meveo.admin.job.logging.JobLoggingInterceptor;
import org.meveo.commons.utils.FileUtils;
import org.meveo.commons.utils.ImportFileFiltre;
import org.meveo.commons.utils.JAXBUtils;
import org.meveo.commons.utils.ParamBean;
import org.meveo.commons.utils.StringUtils;
import org.meveo.interceptor.PerformanceInterceptor;
import org.meveo.model.admin.SubscriptionImportHisto;
import org.meveo.model.billing.UserAccount;
import org.meveo.model.catalog.OfferTemplate;
import org.meveo.model.crm.Provider;
import org.meveo.model.jaxb.subscription.ErrorServiceInstance;
import org.meveo.model.jaxb.subscription.ErrorSubscription;
import org.meveo.model.jaxb.subscription.Errors;
import org.meveo.model.jaxb.subscription.Subscriptions;
import org.meveo.model.jaxb.subscription.WarningSubscription;
import org.meveo.model.jaxb.subscription.Warnings;
import org.meveo.model.jobs.JobExecutionResultImpl;
import org.meveo.service.admin.impl.SubscriptionImportHistoService;
import org.meveo.service.billing.impl.SubscriptionService;
import org.meveo.service.billing.impl.UserAccountService;
import org.meveo.service.catalog.impl.OfferTemplateService;
import org.meveo.service.crm.impl.CheckedSubscription;
import org.meveo.service.crm.impl.ImportIgnoredException;
import org.meveo.service.crm.impl.SubscriptionImportService;
import org.meveo.service.crm.impl.SubscriptionServiceException;
import org.meveo.util.ApplicationProvider;
import org.slf4j.Logger;

@Stateless
public class ImportSubscriptionsJobBean {

	@Inject
	private Logger log;

	@Inject
	private SubscriptionService subscriptionService;

	@Inject
	private SubscriptionImportHistoService subscriptionImportHistoService;

	@Inject
	private OfferTemplateService offerTemplateService;

	@Inject
	private UserAccountService userAccountService;

	@Inject
	private SubscriptionImportService subscriptionImportService;

    @Inject
    @ApplicationProvider
    protected Provider appProvider;
	
	ParamBean paramBean = ParamBean.getInstance();

	Subscriptions subscriptionsError;
	Subscriptions subscriptionsWarning;

	int nbSubscriptions;
	int nbSubscriptionsError;
	int nbSubscriptionsTerminated;
	int nbSubscriptionsIgnored;
	int nbSubscriptionsCreated;
	SubscriptionImportHisto subscriptionImportHisto;

	@Interceptors({ JobLoggingInterceptor.class, PerformanceInterceptor.class })
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void execute(JobExecutionResultImpl result) {

		String importDir = paramBean.getProperty("providers.rootDir", "./opencelldata/") + File.separator
				+ appProvider.getCode() + File.separator + "imports" + File.separator + "subscriptions" + File.separator;

		String dirIN = importDir + "input";
		log.info("dirIN=" + dirIN);
		String dirOK = importDir + "output";
		String dirKO = importDir + "reject";
		String prefix = paramBean.getProperty("connectorCRM.importSubscriptions.prefix", "SUB_");
		String ext = paramBean.getProperty("connectorCRM.importSubscriptions.extension", "xml");

		File dir = new File(dirIN);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		List<File> files = getFilesToProcess(dir, prefix, ext);
		int numberOfFiles = files.size();
		log.info("InputFiles job to import={}", numberOfFiles);

		for (File file : files) {
			File currentFile = null;
			try {
				log.info("InputFiles job {} in progress...", file.getName());
				currentFile = FileUtils.addExtension(file, ".processing");

				importFile(currentFile, file.getName());
				FileUtils.moveFile(dirOK, currentFile, file.getName());
				log.info("InputFiles job {} done.", file.getName());
			} catch (Exception e) {
				log.error("failed to import subscriptions",e);
				log.info("InputFiles job {} failed.", file.getName());
				FileUtils.moveFile(dirKO, currentFile, file.getName());
				log.error("Failed to import subscriptions job",e);
			} finally {
				if (currentFile != null)
					currentFile.delete();
			}
		}
		
		result.setNbItemsToProcess(nbSubscriptions);
		result.setNbItemsCorrectlyProcessed(nbSubscriptionsCreated + nbSubscriptionsTerminated + nbSubscriptionsIgnored);
		result.setNbItemsProcessedWithError(nbSubscriptionsError);
		if (subscriptionsWarning!=null){
		    result.setNbItemsProcessedWithWarning((subscriptionsWarning.getErrors() != null  
		            && subscriptionsWarning.getErrors().getErrorSubscription() != null) ? subscriptionsWarning.getErrors().getErrorSubscription().size() : 0);
		} else {
		    result.setNbItemsProcessedWithWarning(0);
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void importFile(File file, String fileName) throws JAXBException, Exception {
		log.info("start import file :" + fileName);

		subscriptionsError = new Subscriptions();
		subscriptionsWarning = new Subscriptions();
		nbSubscriptions = 0;
		nbSubscriptionsError = 0;
		nbSubscriptionsTerminated = 0;
		nbSubscriptionsIgnored = 0;
		nbSubscriptionsCreated = 0;

		subscriptionImportHisto = new SubscriptionImportHisto();
		subscriptionImportHisto.setExecutionDate(new Date());
		subscriptionImportHisto.setFileName(fileName);

		if (file.length() < 100) {
			createSubscriptionWarning(null, "Empty file.");
			generateReport(fileName);
			createHistory();
			return;
		}

		Subscriptions jaxbSubscriptions = (Subscriptions) JAXBUtils.unmarshaller(Subscriptions.class, file);
		log.debug("parsing file ok");

		int i = -1;
		nbSubscriptions = jaxbSubscriptions.getSubscription().size();
		if (nbSubscriptions == 0) {
			createSubscriptionWarning(null, "Empty file.");
		}

		for (org.meveo.model.jaxb.subscription.Subscription jaxbSubscription : jaxbSubscriptions.getSubscription()) {
			try {
				log.debug("importing subscription index={}, code={}", i++, jaxbSubscription.getCode());
				CheckedSubscription checkSubscription = subscriptionCheckError(jaxbSubscription);

				if (checkSubscription == null) {
					nbSubscriptionsError++;
					log.info("File:" + fileName + ", typeEntity:Subscription, index:" + i + ", code:"
							+ jaxbSubscription.getCode() + ", status:Error");
					continue;
				}

				nbSubscriptionsCreated += subscriptionImportService.importSubscription(checkSubscription,
						jaxbSubscription, fileName, i);
			} catch (ImportIgnoredException ie) {
				log.info("File:" + fileName + ", typeEntity:Subscription, index:" + i + ", code:"
						+ jaxbSubscription.getCode() + ", status:Ignored");
				nbSubscriptionsIgnored++;
			} catch (SubscriptionServiceException se) {
				createServiceInstanceError(se.getSubscrip(), se.getServiceInst(), se.getMess());
				nbSubscriptionsError++;
				log.info("File:" + fileName + ", typeEntity:Subscription, index:" + i + ", code:"
						+ jaxbSubscription.getCode() + ", status:Error");
			} catch (Exception e) {

				// createSubscriptionError(subscrip,
				// ExceptionUtils.getRootCause(e).getMessage());
				createSubscriptionError(jaxbSubscription, e.getMessage());
				nbSubscriptionsError++;
				log.info("File:" + fileName + ", typeEntity:Subscription, index:" + i + ", code:"
						+ jaxbSubscription.getCode() + ", status:Error");
			}
		}

		generateReport(fileName);
		createHistory();
		log.info("end import file ");
	}

	private void createHistory() throws Exception {
		subscriptionImportHisto.setLinesRead(nbSubscriptions);
		subscriptionImportHisto.setLinesInserted(nbSubscriptionsCreated);
		subscriptionImportHisto.setLinesRejected(nbSubscriptionsError);
		subscriptionImportHisto.setNbSubscriptionsIgnored(nbSubscriptionsIgnored);
		subscriptionImportHisto.setNbSubscriptionsTerminated(nbSubscriptionsTerminated);
		subscriptionImportHistoService.create(subscriptionImportHisto);
	}

	private void generateReport(String fileName) throws Exception {
		String importDir = paramBean.getProperty("providers.rootDir", "./opencelldata/") + File.separator
				+ appProvider.getCode() + File.separator + "imports" + File.separator + "subscriptions" + File.separator;

		if (subscriptionsWarning.getWarnings() != null) {
			String warningDir = importDir + "output" + File.separator + "warnings";
			File dir = new File(warningDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			JAXBUtils.marshaller(subscriptionsWarning, new File(warningDir + File.separator + "WARN_" + fileName));
		}

		if (subscriptionsError.getErrors() != null) {
			String errorDir = importDir + "output" + File.separator + "errors";
			File dir = new File(errorDir);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			JAXBUtils.marshaller(subscriptionsError, new File(errorDir + File.separator + "ERR_" + fileName));
		}

	}

	private List<File> getFilesToProcess(File dir, String prefix, String ext) {
		List<File> files = new ArrayList<File>();
		ImportFileFiltre filtre = new ImportFileFiltre(prefix, ext);
		File[] listFile = dir.listFiles(filtre);

		if (listFile == null) {
			return files;
		}

		for (File file : listFile) {
			if (file.isFile()) {
				files.add(file);
				// we just process one file
				return files;
			}
		}
		return files;
	}

	private CheckedSubscription subscriptionCheckError(
			org.meveo.model.jaxb.subscription.Subscription jaxbSubscription) {
		CheckedSubscription checkSubscription = new CheckedSubscription();

		if (StringUtils.isBlank(jaxbSubscription.getCode())) {
			createSubscriptionError(jaxbSubscription, "Code is null.");
			return null;
		}

		if (StringUtils.isBlank(jaxbSubscription.getUserAccountId())) {
			createSubscriptionError(jaxbSubscription, "UserAccountId is null.");
			return null;
		}

		if (StringUtils.isBlank(jaxbSubscription.getOfferCode())) {
			createSubscriptionError(jaxbSubscription, "OfferCode is null.");
			return null;
		}

		if (StringUtils.isBlank(jaxbSubscription.getSubscriptionDate())) {
			createSubscriptionError(jaxbSubscription, "SubscriptionDate is null.");
			return null;
		}

		if (jaxbSubscription.getStatus() == null
				|| StringUtils.isBlank(jaxbSubscription.getStatus().getValue())
				|| ("ACTIVE" + "TERMINATED" + "CANCELED" + "SUSPENDED")
						.indexOf(jaxbSubscription.getStatus().getValue()) == -1) {
			createSubscriptionError(jaxbSubscription,
					"Status is null, or not in { ACTIVE, TERMINATED, CANCELED, SUSPENDED }");

			return null;
		}

		OfferTemplate offerTemplate = null;
		try {
			offerTemplate = offerTemplateService.findByCode(jaxbSubscription.getOfferCode().toUpperCase());
		} catch (Exception e) {
			log.warn("failed to find offerTemplate",e);
		}

		if (offerTemplate == null) {
			createSubscriptionError(jaxbSubscription,
					"Cannot find OfferTemplate with code=" + jaxbSubscription.getOfferCode());
			return null;
		}
		checkSubscription.offerTemplate = offerTemplate;

		UserAccount userAccount = null;
		try {
			userAccount = userAccountService.findByCode(jaxbSubscription.getUserAccountId());
		} catch (Exception e) {
			log.error("error generated while getting user account",e);
		}

		if (userAccount == null) {
			createSubscriptionError(jaxbSubscription,
					"Cannot find UserAccount entity=" + jaxbSubscription.getUserAccountId());
			return null;
		}
		checkSubscription.userAccount = userAccount;

		try {
			checkSubscription.subscription = subscriptionService.findByCode(jaxbSubscription.getCode());
		} catch (Exception e) {
			log.error("failed to find subscription",e);
		}

		if (!"ACTIVE".equals(jaxbSubscription.getStatus().getValue()) && checkSubscription.subscription == null) {
			createSubscriptionError(jaxbSubscription,
					"Cannot find subscription with code=" + jaxbSubscription.getCode());
			return null;
		}

		if ("ACTIVE".equals(jaxbSubscription.getStatus().getValue())) {
			if (jaxbSubscription.getServices() == null || jaxbSubscription.getServices().getServiceInstance() == null
					|| jaxbSubscription.getServices().getServiceInstance().isEmpty()) {
				createSubscriptionError(jaxbSubscription, "Cannot create subscription without services");
				return null;
			}

			for (org.meveo.model.jaxb.subscription.ServiceInstance serviceInst : jaxbSubscription.getServices()
					.getServiceInstance()) {
				if (serviceInstanceCheckError(jaxbSubscription, serviceInst)) {
					return null;
				}

				checkSubscription.serviceInsts.add(serviceInst);
			}

			if (jaxbSubscription.getAccesses() != null) {
				for (org.meveo.model.jaxb.subscription.Access jaxbAccess : jaxbSubscription.getAccesses().getAccess()) {
					if (accessCheckError(jaxbSubscription, jaxbAccess)) {
						return null;
					}

					checkSubscription.accessPoints.add(jaxbAccess);
				}
			}
		}

		return checkSubscription;
	}

	private void createSubscriptionError(org.meveo.model.jaxb.subscription.Subscription subscrip, String cause) {
		log.error(cause);

		String generateFullCrmReject = paramBean.getProperty("connectorCRM.generateFullCrmReject", "true");
		ErrorSubscription errorSubscription = new ErrorSubscription();
		errorSubscription.setCause(cause);
		errorSubscription.setCode(subscrip.getCode());

		if (!subscriptionsError.getSubscription().contains(subscrip) && "true".equalsIgnoreCase(generateFullCrmReject)) {
			subscriptionsError.getSubscription().add(subscrip);
		}

		if (subscriptionsError.getErrors() == null) {
			subscriptionsError.setErrors(new Errors());
		}

		subscriptionsError.getErrors().getErrorSubscription().add(errorSubscription);
	}

	private void createSubscriptionWarning(org.meveo.model.jaxb.subscription.Subscription subscrip, String cause) {
		log.warn(cause);

		String generateFullCrmReject = paramBean.getProperty("connectorCRM.generateFullCrmReject", "true");
		WarningSubscription warningSubscription = new WarningSubscription();
		warningSubscription.setCause(cause);
		warningSubscription.setCode(subscrip == null ? "" : subscrip.getCode());

		if (!subscriptionsWarning.getSubscription().contains(subscrip)
				&& "true".equalsIgnoreCase(generateFullCrmReject) && subscrip != null) {
			subscriptionsWarning.getSubscription().add(subscrip);
		}

		if (subscriptionsWarning.getWarnings() == null) {
			subscriptionsWarning.setWarnings(new Warnings());
		}

		subscriptionsWarning.getWarnings().getWarningSubscription().add(warningSubscription);
	}

	private boolean serviceInstanceCheckError(org.meveo.model.jaxb.subscription.Subscription subscrip,
			org.meveo.model.jaxb.subscription.ServiceInstance serviceInst) {

		if (StringUtils.isBlank(serviceInst.getCode())) {
			createServiceInstanceError(subscrip, serviceInst, "code is null");
			return true;
		}

		if (StringUtils.isBlank(serviceInst.getSubscriptionDate())) {
			createSubscriptionError(subscrip, "SubscriptionDate is null");
			return true;
		}

		return false;
	}

	private boolean accessCheckError(org.meveo.model.jaxb.subscription.Subscription subscrip,
			org.meveo.model.jaxb.subscription.Access access) {

		if (StringUtils.isBlank(access.getAccessUserId())) {
			createSubscriptionError(subscrip, "AccessUserId is null");
			return true;
		}

		return false;
	}

	private void createServiceInstanceError(org.meveo.model.jaxb.subscription.Subscription subscrip,
			org.meveo.model.jaxb.subscription.ServiceInstance serviceInst, String cause) {
		ErrorServiceInstance errorServiceInstance = new ErrorServiceInstance();
		errorServiceInstance.setCause(cause);
		errorServiceInstance.setCode(serviceInst.getCode());
		errorServiceInstance.setSubscriptionCode(subscrip.getCode());

		if (!subscriptionsError.getSubscription().contains(subscrip)) {
			subscriptionsError.getSubscription().add(subscrip);
		}

		if (subscriptionsError.getErrors() == null) {
			subscriptionsError.setErrors(new Errors());
		}

		subscriptionsError.getErrors().getErrorServiceInstance().add(errorServiceInstance);
	}

}
