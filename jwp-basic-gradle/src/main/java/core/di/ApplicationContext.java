package core.di;

import java.util.Set;

public class ApplicationContext {

    private final BeanFactory beanFactory;

    public ApplicationContext(final Object... basePackages) {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackages);
        beanFactory.initialize();
    }

    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }

    public <T> T getBean(final Class<T> beanClass) {
        return beanFactory.getBean(beanClass);
    }
}
