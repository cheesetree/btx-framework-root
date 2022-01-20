package top.cheesetree.btx.framework.security.cas.annotation;

import org.springframework.context.annotation.Import;
import top.cheesetree.btx.framework.security.cas.config.CasClientConfiguration;

import java.lang.annotation.*;

/**
 * @Author: van
 * @Date: 2021/10/25 17:22
 * @Description: TODO
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CasClientConfiguration.class)
public @interface EnableCasClient {

    enum ValidationType {
        //cas
        CAS,
        //cas3
        CAS3,
        //saml
        SAML
    }
}
