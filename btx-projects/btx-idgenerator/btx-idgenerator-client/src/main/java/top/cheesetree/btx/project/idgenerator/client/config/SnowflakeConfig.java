package top.cheesetree.btx.project.idgenerator.client.config;

import lombok.Data;

import java.util.List;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/11/13 14:42
 * @Version: 1.0
 * @Description:
 */
@Data
public class SnowflakeConfig {
    private boolean enable = true;
    private String interfaceName;
    private int workId = -1;
    private List<String> workips;
    private int timeOffset = 100;
}
