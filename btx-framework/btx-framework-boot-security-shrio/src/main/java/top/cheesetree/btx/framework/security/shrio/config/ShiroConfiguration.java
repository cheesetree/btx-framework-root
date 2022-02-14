package top.cheesetree.btx.framework.security.shrio.config;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.security.config.BtxSecurityProperties;
import top.cheesetree.btx.framework.security.shrio.filter.BtxSecurityShiroFormFilter;
import top.cheesetree.btx.framework.security.shrio.filter.BtxSecurityShiroPermissionsFilter;
import top.cheesetree.btx.framework.security.shrio.filter.BtxSecurityShiroUserFilter;
import top.cheesetree.btx.framework.security.shrio.matcher.BtxNoAuthCredentialsMatcher;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityAuthorizingRealm;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityJWTAuthorizingRealm;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityOAuthTokenAuthorizingRealm;

import javax.servlet.Filter;
import java.util.*;

/**
 * @Author: van
 * @Date: 2022/1/12 13:25
 * @Description: TODO
 */
@Configuration
@EnableConfigurationProperties({BtxSecurityShiroProperties.class})
public class ShiroConfiguration {
    @Autowired
    BtxSecurityProperties btxSecurityProperties;
    //    @Autowired
//    BtxSecurityAuthorizingRealm btxSecurityAuthorizingRealm;
    @Autowired
    BtxSecurityOAuthTokenAuthorizingRealm btxSecurityOAuthTokenAuthorizingRealm;
    @Autowired
    BtxSecurityJWTAuthorizingRealm btxSecurityJWTRealm;

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
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
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
        if (!StringUtils.isEmpty(btxSecurityProperties.getLoginPath())) {
            shiroFilterFactoryBean.setLoginUrl(btxSecurityProperties.getLoginPath());
            filterChainDefinitionMap.put(btxSecurityProperties.getLoginPath(), "anon");
        }
        //设置未授权页面
        if (!StringUtils.isEmpty(btxSecurityProperties.getNoAuthPath())) {
            shiroFilterFactoryBean.setUnauthorizedUrl(btxSecurityProperties.getNoAuthPath());
            filterChainDefinitionMap.put(btxSecurityProperties.getNoAuthPath(), "anon");
        }
        //设置错误页面
        if (!StringUtils.isEmpty(btxSecurityProperties.getErrorPath())) {
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


    /**
     * 注入 securityManager
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        List<Realm> rs = new ArrayList<>();
        rs.add(btxSecurityOAuthTokenAuthorizingRealm);
        rs.add(btxSecurityJWTRealm);
        rs.add(btxSecurityAuthorizingRealm());

        securityManager.setAuthenticator(authenticator());
        securityManager.setRealms(rs);

        return securityManager;
    }

    @Bean
    public BtxSecurityAuthorizingRealm btxSecurityAuthorizingRealm() {
        BtxSecurityAuthorizingRealm r = new BtxSecurityAuthorizingRealm(new BtxNoAuthCredentialsMatcher());
        return r;
    }

}
