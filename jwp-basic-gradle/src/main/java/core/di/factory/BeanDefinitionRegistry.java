package core.di.factory;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(final Class<?> clazz, final DefaultBeanDefinition defaultBeanDefinition);

}
