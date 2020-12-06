package bml.quartz.job;

import bml.business.entity.User;
import bml.business.service.UserService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 边月白
 * Date 2020-11-04 14:22
 */
public class OneJob extends QuartzJobBean {

    @Resource
    UserService userService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = userService.getById(1);
        System.out.println("第一任务输出用户名："+user.getUsername()+"当前时间："+dateFormat.format(new Date()));
    }

}
