package bml.quartz.job;

import bml.business.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 边月白
 * Date 2020-11-04 14:26
 */
public class TwoJob implements Job {

    @Resource
    UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = userService.count(null);
        System.out.println("模拟午夜统计总人数"+dateFormat.format(new Date())+"用户总数是："+count);
    }
}
