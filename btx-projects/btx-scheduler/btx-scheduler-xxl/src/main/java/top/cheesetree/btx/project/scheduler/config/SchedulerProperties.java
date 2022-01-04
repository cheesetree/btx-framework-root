package top.cheesetree.btx.project.scheduler.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/4 15:53
 * @Version: 1.0
 * @Description:
 */
@ConfigurationProperties("btx.scheduler.xxl")
@Data
public class SchedulerProperties {
    private String adminAddresses;

    private String accessToken;

    private String appname;

    private String address;

    private String ip;

    private int port;

    private String logPath;

    private int logRetentionDays;
}
