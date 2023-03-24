package core.di.beans.factory.support;

public interface BeanDefinitionReader {

    void loadBeanDefinitions(final Class<?>... annotatedClasses);

}
