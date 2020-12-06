package bml.quartz.service;

import bml.quartz.entity.QuartzJobVO;
import bml.quartz.job.OneJob;
import bml.quartz.job.TwoJob;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author 边月白
 * Date 2020-11-04 14:28
 */
@Configuration
public class QuartzService {

    @Resource
    Scheduler scheduler;

    public void prepareJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail1 = newJob(OneJob.class)
                .withIdentity("job1", "group1")
                .withDescription("任务一描述：每隔10秒输出用户一的用户名")
                .build();
        JobDetail jobDetail2 = newJob(TwoJob.class)
                .withIdentity("job2", "group1")
                .withDescription("任务二描述: 模拟每天午夜时统计一下总人数")
                .build();

        CronTrigger cronTrigger1 = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/10 * 10-23 * * ?")
                                .withMisfireHandlingInstructionDoNothing())
                .build();
        CronTrigger cronTrigger2 = newTrigger()
                .withIdentity("trigger2", "group1")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("0/30 * 10-23 * * ?")
                                .withMisfireHandlingInstructionDoNothing())
                .build();
        scheduler.scheduleJob(jobDetail1, cronTrigger1);
        scheduler.scheduleJob(jobDetail2, cronTrigger2);
    }

    public void startJob() throws SchedulerException {
        prepareJob(scheduler);
        scheduler.start();
    }


    /**
     * 获取任务列表
     */
    public List<QuartzJobVO> getList() throws SchedulerException {
        List<QuartzJobVO> list = new ArrayList<>();
        // 再获取Scheduler下的所有group
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String groupName : triggerGroupNames) {
            // 组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            // 获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                // 通过triggerKey在scheduler中获取trigger对象
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                // 获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                // 组装页面需要显示的数据
                QuartzJobVO quartzJobsVO = new QuartzJobVO();
                quartzJobsVO.setJobName(jobDetail.getName());
                quartzJobsVO.setJobGroup(groupName);
                quartzJobsVO.setDescription(jobDetail.getDescription());
                // 获取全类名 也可以获取包名
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                quartzJobsVO.setJobClassName(jobClass.getName());
                quartzJobsVO.setTriggerName(triggerKey.getName());
                quartzJobsVO.setTriggerGroup(triggerKey.getGroup());
                // 任务当前状态
                quartzJobsVO.setTriggerState(scheduler.getTriggerState(triggerKey).toString());
                quartzJobsVO.setCronExpression(trigger.getCronExpression());
                quartzJobsVO.setTimeZone(trigger.getTimeZone().getID());
                list.add(quartzJobsVO);
            }
        }
        return list;
    }

    /**
     * 动态添加一个定时任务 类名一定要写全
     * @param className 根据类名得到类 比如：bml.job.job.OneJob
     */
    public void addJob(String jobName, String triggerName, String description, String className, String cron) throws SchedulerException, ClassNotFoundException {
        JobDetail jobDetail = newJob((Class<? extends Job>) Class.forName(className))
                .withIdentity(jobName, "group1")
                .withDescription(description)
                .build();
        CronTrigger cronTrigger = newTrigger()
                .withIdentity(triggerName, "group1")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cron)
                                .withMisfireHandlingInstructionDoNothing())
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     *  shutdown(true)表示等待所有正在执行的任务执行完毕后关闭Scheduler
     *  shutdown(false),即shutdown()表示直接关闭Scheduler
     */
    public void shutdown() throws SchedulerException {
        scheduler.shutdown(true);
    }

    /**
     * 02-暂停某个任务
     */
    public void pauseOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 03-暂停所有任务
     * 区别在于触发失火指令应用行为.
     * 在standby()后调用start()时,任何在待机状态下出现的失火将被忽略.
     * 当您在pauseAll()之后调用resumeAll()时,将会应用在调度程序暂停时出现的所有失火.
     * standby之后就不能再条用了？
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 04-恢复一个任务
     */
    public void resumeOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 05-恢复所有任务
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 06-修改某个任务的执行时间
     */
    public boolean updateJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return date != null;
    }

    /**
     * 07-删除某个任务 删除后该任务将不再执行
     */
    public void deleteOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
    }
}
