package top.cheesetree.btx.framework.database.hikari;

import top.cheesetree.btx.framework.database.hikari.config.BtxDataSourceConfigure;
import com.zaxxer.hikari.HikariDataSource;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2020/10/27 14:36
 * @Version: 1.0
 * @Description:
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

        return new String(AESDecode(Base64.getUrlDecoder().decode(pwd), key, iv.getBytes("UTF-8")),
                "UTF-8");
    }

    byte[] AESDecode(byte[] message, String sKey, byte[] iv)
            throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(sKey.getBytes("UTF-8"));
        kgen.init(128, random);
        SecretKey secretKey = kgen.generateKey();
        byte[] enCodeFormat = secretKey.getEncoded();
        SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器

        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));// 初始化
        return cipher.doFinal(message);
    }
}
