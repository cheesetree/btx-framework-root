package top.cheesetree.btx.framework.web.session.config;

import lombok.Getter;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.session.SaveMode;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2021/4/2 15:05
 * @Version: 1.0
 * @Description:
 */
@Getter
public class BtxSessionConfigProperties {
    private String namespace;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration timeout;
    private SaveMode saveMode;

    public BtxSessionConfigProperties() {
        namespace = PropertiesUtil.getProperties().getStringProperty("btx.session.namespace", "spring:session");
        timeout = Duration.parse(PropertiesUtil.getProperties().getStringProperty("sbpc.session.timeout", "PT30M"));
        saveMode = SaveMode.valueOf(PropertiesUtil.getProperties().getStringProperty("sbpc.session.savemode",
                SaveMode.ON_SET_ATTRIBUTE.toString()));
    }

}
