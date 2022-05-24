package com.alibaba.boot.nacos.config.binder;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.spring.context.properties.config.NacosConfigurationPropertiesBinder;
import com.alibaba.nacos.spring.core.env.NacosPropertySource;
import com.alibaba.nacos.spring.util.ObjectUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Method;

/**
 * @author van
 * @date 2022/5/23 16:13
 * @description TODO
 */
public class NacosBootConfigurationPropertiesBinder extends NacosConfigurationPropertiesBinder {
    private final ConfigurableApplicationContext applicationContext;

    private final StandardEnvironment environment = new StandardEnvironment();

    public NacosBootConfigurationPropertiesBinder(
            ConfigurableApplicationContext applicationContext) {
        super(applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    protected void doBind(Object bean, String beanName, String dataId, String groupId,
                          String configType, NacosConfigurationProperties properties, String content,
                          ConfigService configService) {
        synchronized (this) {
            String name = "nacos-bootstrap-" + beanName;
            NacosPropertySource propertySource = new NacosPropertySource(dataId, groupId, name, content, configType);
            environment.getPropertySources().addLast(propertySource);
            ObjectUtils.cleanMapOrCollectionField(bean);
            Binder binder = Binder.get(environment);
            ResolvableType type = getBeanType(bean, beanName);
            Bindable<?> target = Bindable.of(type).withExistingValue(bean);
            binder.bind(properties.prefix(), target);
            publishBoundEvent(bean, beanName, dataId, groupId, properties, content, configService);
            publishMetadataEvent(bean, beanName, dataId, groupId, properties);
            environment.getPropertySources().remove(name);
        }
    }

    private ResolvableType getBeanType(Object bean, String beanName) {
//        Method factoryMethod = findFactoryMethod(beanName);
//        if (factoryMethod != null) {
//            return ResolvableType.forMethodReturnType(factoryMethod);
//        }
        return ResolvableType.forClass(bean.getClass());
    }

    public Method findFactoryMethod(String beanName) {
        ConfigurableListableBeanFactory beanFactory = this.applicationContext.getBeanFactory();
        if (beanFactory.containsBeanDefinition(beanName)) {
            BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
            if (beanDefinition instanceof RootBeanDefinition) {
                return ((RootBeanDefinition) beanDefinition).getResolvedFactoryMethod();
            }
        }
        return null;
    }
}
