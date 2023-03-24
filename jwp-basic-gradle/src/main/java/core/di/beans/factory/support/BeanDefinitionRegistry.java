package core.di.beans.factory.support;

import core.di.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(final Class<?> clazz, final BeanDefinition beanDefinition);

}
