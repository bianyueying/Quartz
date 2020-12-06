package bml.quartz.controller;

import bml.config.BmlResult;
import bml.quartz.entity.QuartzJobVO;
import bml.quartz.service.QuartzService;
import org.quartz.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 边月白
 * Date 2020-11-04 14:59
 */
@RestController
public class QuartzController {

    @Resource
    QuartzService quartzService;

    @PostMapping("/quartz/list")
    public BmlResult<Object> getAllJobs() {
        Map<String, Object> resultMap = new HashMap<>(100);
        try {
            List<QuartzJobVO> list = quartzService.getList();
            resultMap.put("status",0);
            resultMap.put("count",list.size());
            resultMap.put("data",list);
        } catch (SchedulerException e) {
            resultMap.put("status",1);
            resultMap.put("count",0);
            resultMap.put("data",null);
            e.printStackTrace();
        }
        return new BmlResult<>(resultMap,0);
    }

    @PostMapping("/quartz/start")
    public BmlResult<Object> startQuartzJob() {
        try {
            quartzService.startJob();
            return new BmlResult<>(200,"启动成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"启动失败...");
        }
    }

    @PostMapping(value = "/quartz/pause")
    public BmlResult<Object> pauseQuartzJob(String name, String group) {
        try {
            quartzService.pauseOneJob(name, group);
            return new BmlResult<>(200,"暂停成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"暂停失败...");
        }
    }

    @PostMapping(value = "/quartz/pauseAll")
    public BmlResult<Object> shutQuartzJob() {
        try {
            quartzService.pauseAllJob();
            return new BmlResult<>(200,"暂停成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"暂停失败...");
        }
    }

    @PostMapping(value = "/quartz/resume")
    public BmlResult<Object> resumeOneJob(String name, String group) {
        try {
            quartzService.resumeOneJob(name, group);
            return new BmlResult<>(200,"恢复成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"恢复失败...");
        }
    }

    @PostMapping(value = "/quartz/resumeAll")
    public BmlResult<Object> resumeAll() {
        try {
            quartzService.resumeAllJob();
            return new BmlResult<>(200,"恢复成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"恢复失败...");
        }
    }

    @PostMapping(value = "/quartz/delete")
    public BmlResult<Object> delete(String name, String group) {
        try {
            // 从数据库中删除该任务 无法恢复
            quartzService.deleteOneJob(name, group);
            return new BmlResult<>(200,"删除成功！");
        } catch (SchedulerException e) {
            e.printStackTrace();
            return new BmlResult<>(400,"删除失败...");
        }
    }

    @PostMapping(value = "/quartz/update")
    public BmlResult<Object> modifyJob(@RequestParam("triggerName") String name,
                                       @RequestParam("triggerGroup") String group,
                                       @RequestParam("cronExpression") String cron) throws SchedulerException {
        boolean b = quartzService.updateJob(name, group, cron);
        if (b) {
            return new BmlResult<>(200,"更新成功！");
        } else {
            return new BmlResult<>(400,"更新失败...");
        }
    }

    @PostMapping(value = "/quartz/add")
    public BmlResult<Object> addJob(@RequestParam("add_JobName") String jobName,
                                    @RequestParam("add_triggerName") String triggerName,
                                    @RequestParam("add_description") String description,
                                    @RequestParam("add_JobClass") String className,
                                    @RequestParam("add_cron") String cron) {
        try {
            quartzService.addJob(jobName, triggerName, description, className, cron);
            return new BmlResult<>(200,"添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new BmlResult<>(400,"添加失败...");
        }
    }
}


















