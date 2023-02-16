package com.web.puppylink.config;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

public class SchedulerConfig  implements SchedulingConfigurer  {
	
//    @Value("${thread.pool.size}")
    private int POOL_SIZE = 1;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix("현재 쓰레드-");
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
    }
}
