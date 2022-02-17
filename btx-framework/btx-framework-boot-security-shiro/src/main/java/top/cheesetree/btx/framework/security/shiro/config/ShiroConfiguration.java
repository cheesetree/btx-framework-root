package top.cheesetree.btx.framework.security.shiro.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.security.config.BtxSecurityProperties;
import top.cheesetree.btx.framework.security.shiro.filter.BtxSecurityShiroFormFilter;
import top.cheesetree.btx.framework.security.shiro.filter.BtxSecurityShiroPermissionsFilter;
import top.cheesetree.btx.framework.security.shiro.filter.BtxSecurityShiroUserFilter;
import top.cheesetree.btx.framework.security.shiro.matcher.BtxNoAuthCredentialsMatcher;
import top.cheesetree.btx.framework.security.shiro.realm.custom.BtxSecurityAuthorizingRealm;
import top.cheesetree.btx.framework.security.shiro.realm.jwt.BtxSecurityJWTAuthorizingRealm;
import top.cheesetree.btx.framework.security.shiro.realm.oauth.BtxSecurityOAuthAuthorizingRealm;

import javax.servlet.Filter;
import java.util.*;

/**
 * @Author: van
 * @Date: 2022/1/12 13:25
 * @Description: TODO
 */
@Configuration
@EnableConfigurationProperties({BtxShiroProperties.class, BtxShiroCacheProperties.class})
@Slf4j
public class ShiroConfiguration {
    @Autowired
    BtxSecurityProperties btxSecurityProperties;
    @Autowired
    BtxShiroCacheProperties BtxShiroCacheProperties;
    @Autowired
    BtxShiroProperties btxShiroProperties;

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //设置过滤器
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        filterMap.put("authc", new BtxSecurityShiroFormFilter());
        filterMap.put("user", new BtxSecurityShiroUserFilter());
        filterMap.put("perms", new BtxSecurityShiroPermissionsFilter());

        shiroFilterFactoryBean.setFilters(filterMap);
        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        if (btxSecurityProperties.getContextInterceptorExcludePathPatterns() != null) {
            //匿名访问
            Arrays.stream(btxSecurityProperties.getContextInterceptorExcludePathPatterns()).forEach((String extpath) -> {
                filterChainDefinitionMap.put(extpath, "anon");
            });
        }

        //设置登录页面
        if (StringUtils.hasLength(btxSecurityProperties.getLoginPath())) {
            shiroFilterFactoryBean.setLoginUrl(btxSecurityProperties.getLoginPath());
            filterChainDefinitionMap.put(btxSecurityProperties.getLoginPath(), "anon");
        }
        //设置未授权页面
        if (StringUtils.hasLength(btxSecurityProperties.getNoAuthPath())) {
            shiroFilterFactoryBean.setUnauthorizedUrl(btxSecurityProperties.getNoAuthPath());
            filterChainDefinitionMap.put(btxSecurityProperties.getNoAuthPath(), "anon");
        }
        //设置错误页面
        if (StringUtils.hasLength(btxSecurityProperties.getErrorPath())) {
            filterChainDefinitionMap.put(btxSecurityProperties.getErrorPath(), "anon");
        }
        filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public Authenticator authenticator() {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    @ConditionalOnProperty(value = "btx.security.shiro.cache.cacheType", havingValue = "INNER", matchIfMissing = true)
    public org.apache.shiro.cache.CacheManager shiroCacheManager() {
        return new MemoryConstrainedCacheManager();
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(btxShiroProperties.getSessionTimeOut());
        return sessionManager;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        List<Realm> rs = new ArrayList<>();
        rs.add(btxSecurityOAuthTokenAuthorizingRealm());
        rs.add(btxSecurityJWTAuthorizingRealm());
        rs.add(btxSecurityAuthorizingRealm());

        securityManager.setAuthenticator(authenticator());
        securityManager.setRealms(rs);

        if (BtxShiroCacheProperties.isEnabled()) {
            CacheManager cm = shiroCacheManager();
            securityManager.setCacheManager(cm);
        }

        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    @Bean
    public BtxSecurityAuthorizingRealm btxSecurityAuthorizingRealm() {
        BtxSecurityAuthorizingRealm r = new BtxSecurityAuthorizingRealm(new BtxNoAuthCredentialsMatcher());
        r.setAuthenticationCachingEnabled(true);
        r.setAuthorizationCachingEnabled(true);
        r.setAuthenticationCacheName(btxShiroProperties.getAuthenticationCacheName());
        r.setAuthorizationCacheName(btxShiroProperties.getAuthorizationCacheName());
        return r;
    }

    @Bean
    public BtxSecurityOAuthAuthorizingRealm btxSecurityOAuthTokenAuthorizingRealm() {
        BtxSecurityOAuthAuthorizingRealm r = new BtxSecurityOAuthAuthorizingRealm(new BtxNoAuthCredentialsMatcher());
        return r;
    }

    @Bean
    public BtxSecurityJWTAuthorizingRealm btxSecurityJWTAuthorizingRealm() {
        BtxSecurityJWTAuthorizingRealm r = new BtxSecurityJWTAuthorizingRealm(new BtxNoAuthCredentialsMatcher());
        return r;
    }
}
