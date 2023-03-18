package core.di;

import core.di.factory.BeanFactory;
import core.di.factory.ClasspathBeanDefinitionScanner;
import java.util.Set;

public class ApplicationContext {
    private final BeanFactory beanFactory;


    public ApplicationContext(Object... basePackages) {
        /**
         * beanFactory의 초기화 및 주입 초기화
         */
        this.beanFactory = new BeanFactory();
        final var scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackages);
        beanFactory.initialize();
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }

}
