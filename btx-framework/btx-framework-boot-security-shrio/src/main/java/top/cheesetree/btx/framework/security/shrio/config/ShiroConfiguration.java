package top.cheesetree.btx.framework.security.shrio.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import top.cheesetree.btx.framework.security.config.BtxSecurityProperties;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityAuthorizingRealm;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityJWTAuthorizingRealm;
import top.cheesetree.btx.framework.security.shrio.realm.BtxSecurityTokenAuthorizingRealm;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: van
 * @License:
 * @Contact:
 * @Date: 2022/1/12 13:25
 * @Version: 1.0
 * @Description:
 */
@Configuration
@EnableConfigurationProperties({BtxSecurityShiroProperties.class})
public class ShiroConfiguration {
    @Autowired
    BtxSecurityProperties btxSecurityProperties;
    @Autowired
    BtxSecurityShiroProperties btxSecurityShiroProperties;
    @Autowired
    BtxSecurityAuthorizingRealm btxSecurityRealm;
    @Autowired
    BtxSecurityTokenAuthorizingRealm btxSecurityTokenRealm;
    @Autowired
    BtxSecurityJWTAuthorizingRealm btxSecurityJWTRealm;

    /**
     * 开启Shiro注解(如@RequiresRoles,@RequiresPermissions)
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setUsePrefix(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

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
            shiroFilterFactoryBean.setLoginUrl(btxSecurityProperties.getNoAuthPath());
            filterChainDefinitionMap.put(btxSecurityProperties.getNoAuthPath(), "anon");
        }

        //设置错误页面
        if (!StringUtils.isEmpty(btxSecurityProperties.getErrorPath())) {
            filterChainDefinitionMap.put(btxSecurityProperties.getErrorPath(), "anon");
        }

        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入自定义认证
        switch (btxSecurityShiroProperties.getAuthType()) {
            case TOKEN:
                securityManager.setRealm(btxSecurityTokenRealm);
                break;
            case JWT:
                securityManager.setRealm(btxSecurityJWTRealm);
                break;
            case PASSWORD:
            default:
                securityManager.setRealm(btxSecurityRealm);
                break;
        }

        return securityManager;
    }
}
