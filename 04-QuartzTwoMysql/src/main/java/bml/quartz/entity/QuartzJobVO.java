package bml.quartz.entity;

import lombok.Data;

/**
 * @author 边月白
 * Date 2020-11-05 9:46
 */
@Data
public class QuartzJobVO {

    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private String triggerName;
    private String triggerGroup;
    private String triggerState;
    private String cronExpression;
    private String timeZone;

}
