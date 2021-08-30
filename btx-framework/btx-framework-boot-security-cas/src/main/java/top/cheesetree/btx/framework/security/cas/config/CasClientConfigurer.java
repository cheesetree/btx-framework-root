package top.cheesetree.btx.framework.security.cas.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;

public interface CasClientConfigurer {

    /**
     * Configure or customize CAS authentication filter.
     *
     * @param authenticationFilter
     */
    void configureAuthenticationFilter(FilterRegistrationBean authenticationFilter);

    /**
     * Configure or customize CAS validation filter.
     *
     * @param validationFilter
     */
    void configureValidationFilter(FilterRegistrationBean validationFilter);

    /**
     * Configure or customize CAS http servlet wrapper filter.
     *
     * @param httpServletRequestWrapperFilter
     */
    void configureHttpServletRequestWrapperFilter(FilterRegistrationBean httpServletRequestWrapperFilter);

    /**
     * Configure or customize CAS assertion thread local filter.
     *
     * @param assertionThreadLocalFilter
     */
    void configureAssertionThreadLocalFilter(FilterRegistrationBean assertionThreadLocalFilter);
}
