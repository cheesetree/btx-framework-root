package top.cheesetree.btx.project.scheduler;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.project.scheduler.config.SchedulerProperties;

import javax.annotation.Resource;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/4 16:00
 * @Version: 1.0
 * @Description:
 */
@Component
@EnableConfigurationProperties({SchedulerProperties.class})
public class SchedulerStarter {
    @Resource
    SchedulerProperties schedulerProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(schedulerProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(schedulerProperties.getAppname());
        xxlJobSpringExecutor.setAddress(schedulerProperties.getAddress());
        xxlJobSpringExecutor.setIp(schedulerProperties.getIp());
        xxlJobSpringExecutor.setPort(schedulerProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(schedulerProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(schedulerProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(schedulerProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
