package com.example.demo.quartz;

import com.example.demo.quartz.jobs.SampleJob;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzJobSubmitConfiguration {

    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";
    private static final String CRON_EVERY_ONE_MINUTE = "0 0/1 * ? * * *";

    @Bean(name = "SampleJob")
    public JobDetailFactoryBean jobMemberClassStats() {
        return AppQuartzConfig.createJobDetail(SampleJob.class, "Simple Sample Job");
    }

    @Bean(name = "FetchJobTrigger")
    public CronTriggerFactoryBean triggerMemberClassStats(@Qualifier("SampleJob") JobDetail jobDetail) {
        return AppQuartzConfig.createCronTrigger(jobDetail, CRON_EVERY_ONE_MINUTE, "Sample Job Trigger");
    }

}