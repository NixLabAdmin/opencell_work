package org.meveo.admin.job.importexport;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.meveo.model.admin.User;
import org.meveo.model.crm.CustomFieldTemplate;
import org.meveo.model.jobs.JobCategoryEnum;
import org.meveo.model.jobs.JobExecutionResultImpl;
import org.meveo.model.jobs.TimerEntity;
import org.meveo.model.jobs.TimerInfo;
import org.meveo.service.admin.impl.UserService;
import org.meveo.service.job.Job;
import org.meveo.service.job.JobExecutionService;
import org.meveo.service.job.TimerEntityService;
import org.slf4j.Logger;

@Startup
@Singleton
public class ImportCustomersJob implements Job {

	@Resource
	private TimerService timerService;

	@Inject
	private JobExecutionService jobExecutionService;

	@Inject
	private Logger log;

	@Inject
	private ImportCustomersJobBean importCustomersJobBean;

	@Inject
	private UserService userService;

	@PostConstruct
	public void init() {
		TimerEntityService.registerJob(this);
	}

	@Override
	@Asynchronous
	public void execute(TimerEntity timerEntity, User currentUser) {
		JobExecutionResultImpl result = new JobExecutionResultImpl();
		TimerInfo info = timerEntity.getTimerInfo();
		log.debug("execute impCust, info={}, currentUser={}", info, currentUser);
		if (!running && (info.isActive() || currentUser != null)) {
			try {
				running = true;
				if (currentUser == null) {
					currentUser = userService.findByIdLoadProvider(info.getUserId());
					log.debug("execute impCust, found user from info {}", currentUser);
				}
				importCustomersJobBean.execute(result, currentUser);
				log.debug("execute impCust, persist job execution");
				jobExecutionService.persistResult(this, result, timerEntity, currentUser, getJobCategory());
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			} finally {
				running = false;
			}
			log.debug("end impCust rerating");
		}
	}

	@Override
	public Timer createTimer(ScheduleExpression scheduleExpression, TimerEntity infos) {
		TimerConfig timerConfig = new TimerConfig();
		timerConfig.setInfo(infos);
		timerConfig.setPersistent(false);

		return timerService.createCalendarTimer(scheduleExpression, timerConfig);
	}

	boolean running = false;

	@Override
	@Timeout
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void trigger(Timer timer) {
		execute((TimerEntity) timer.getInfo(), null);
	}

	@Override
	public JobExecutionService getJobExecutionService() {
		return jobExecutionService;
	}

	@Override
	public void cleanAllTimers() {
		Collection<Timer> alltimers = timerService.getTimers();
		log.info("Cancel " + alltimers.size() + " timers for" + this.getClass().getSimpleName());

		for (Timer timer : alltimers) {
			try {
				timer.cancel();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
	}

	@Override
	public JobCategoryEnum getJobCategory() {
		return JobCategoryEnum.IMPORT_HIERARCHY;
	}

	@Override
	public List<CustomFieldTemplate> getCustomFields(User currentUser) {
		// TODO Auto-generated method stub
		return null;
	}

}