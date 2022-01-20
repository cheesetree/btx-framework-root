package top.cheesetree.btx.framework.database.hikari;

import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.database.hikari.config.BtxDataSourceConfigure;

import java.util.Base64;

/**
 * @Author: van
 * @Date: 2020/10/27 14:36
 * @Description: TODO
 */
@Component
public class BtxHikariBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    BtxDataSourceConfigure btxDataSourceConfigure;

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof HikariDataSource) {
            if (!StringUtils.isEmpty(btxDataSourceConfigure.getAppId()) && !StringUtils.isEmpty(btxDataSourceConfigure.getSecretKey())) {
                HikariDataSource hikariDataSource = (HikariDataSource) bean;
                hikariDataSource.setPassword(getSecretResult(btxDataSourceConfigure.getAppId(),
                        btxDataSourceConfigure.getSecretKey(),
                        hikariDataSource.getPassword()));
                bean = hikariDataSource;
            }
        }
        return bean;
    }

    String getSecretResult(String appid, String secretkey, String pwd) throws Exception {
        String key = BtxHikariConfigure.MD5Encode(String.format("%s_%s_%s", appid, secretkey, appid));
        String iv = key.substring(3, 19);

        return new String(BtxHikariConfigure.AESDecode(Base64.getUrlDecoder().decode(pwd), key, iv.getBytes("UTF-8")),
                "UTF-8");
    }
}
