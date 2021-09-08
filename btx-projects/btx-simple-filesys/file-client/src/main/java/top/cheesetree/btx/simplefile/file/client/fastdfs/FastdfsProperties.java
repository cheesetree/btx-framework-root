package top.cheesetree.btx.simplefile.file.client.fastdfs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/15 09:02
 * @Version: 1.0
 * @Description:
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "btx.file.fastdfs")
public class FastdfsProperties {

    private String connectTimeout = "5";
    private String networkTimeout = "30";
    private String charset = "UTF-8";
    private String httpAntiStealToken = "false";
    private String httpSecretKey = "";
    private String httpTrackerHttpPort = "";
    private String trackerServers = "";
    private String nginxServers = "";
    private int connectionPoolMaxTotalPerEntry = 100;
    private int connectionPoolIdleTime = 3600;
    private int connectionPoolMaxWaitTimeInMs = 1000;

}