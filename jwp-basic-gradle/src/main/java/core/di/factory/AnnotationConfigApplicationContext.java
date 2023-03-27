package core.di.factory;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AnnotationConfigApplicationContext {
    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Object... basePackages) {
        beanFactory = new BeanFactory();
        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackages);
        beanFactory.initialize();
    }

    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        List<Object> basePackages = Lists.newArrayList();
        for (Class<?> annotatedClass : annotatedClasses) {
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                continue;
            }
            for (String basePackage : componentScan.value()) {
                // log.info("Component Scan basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
