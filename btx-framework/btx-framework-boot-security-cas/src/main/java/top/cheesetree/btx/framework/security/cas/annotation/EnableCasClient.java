package top.cheesetree.btx.framework.security.cas.annotation;

import org.springframework.context.annotation.Import;
import top.cheesetree.btx.framework.security.cas.config.CasClientConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CasClientConfiguration.class)
public @interface EnableCasClient {

    enum ValidationType {
        CAS,
        CAS3,
        SAML
    }
}
