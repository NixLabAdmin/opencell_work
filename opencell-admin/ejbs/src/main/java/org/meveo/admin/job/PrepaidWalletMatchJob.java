package org.meveo.admin.job;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.meveo.admin.exception.BusinessException;
import org.meveo.model.jobs.JobCategoryEnum;
import org.meveo.model.jobs.JobExecutionResultImpl;
import org.meveo.model.jobs.JobInstance;
import org.meveo.service.job.Job;

@Startup
@Singleton
@Lock(LockType.READ)
public class PrepaidWalletMatchJob extends Job {

    @Inject
    private PrepaidWalletMatchJobBean prepaidWalletMatchJobBean;

    @Override
    protected void execute(JobExecutionResultImpl result, JobInstance jobInstance) throws BusinessException {
        prepaidWalletMatchJobBean.execute(jobInstance.getParametres(), result);

    }

    @Override
    public JobCategoryEnum getJobCategory() {
        return JobCategoryEnum.WALLET;
    }
}