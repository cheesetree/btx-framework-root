package top.cheesetree.btx.simplefile.file.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import top.cheesetree.btx.simplefile.file.client.fastdfs.FastdfsProperties;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/12/17 10:07
 * @Version: 1.0
 * @Description:
 */
@EnableConfigurationProperties({FastdfsProperties.class})
@Component
public class FileClinetConfig {
}
