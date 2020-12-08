package com.fzufood.service.job;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Author qizong007
 * @create 2020/12/8 19:55
 */
@Component
public class Timer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        schedule();
        System.out.println("热门定时器启动");
    }

    public void schedule() throws Exception {
        //创建调度器
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        //定义一个触发器
        Trigger trigger = newTrigger().withIdentity("WindowServiceTrigger", "WindowService") //定义名称和所属的组
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInHours(24) //每隔一天执行一次
                        .repeatForever()) // 无限重复
                .build();

        //定义一个JobDetail
        JobDetail job = newJob(PopularJob.class) //指定干活的类
                .withIdentity("PopularJob", "PopularGroup") //定义任务名称和分组
                //.usingJobData("email", "admin@10086.com") //定义属性
                .build();

        //调度加入这个job
        scheduler.scheduleJob(job, trigger);

        //启动
        scheduler.start();
    }

}
