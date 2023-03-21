package core.di;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(final Class<?> clazz, final BeanDefinition beanDefinition);

}
