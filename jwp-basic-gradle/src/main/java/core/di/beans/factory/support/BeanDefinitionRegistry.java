package core.di.beans.factory.support;

import core.di.beans.factory.support.DefaultBeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(final Class<?> clazz, final DefaultBeanDefinition defaultBeanDefinition);

}
