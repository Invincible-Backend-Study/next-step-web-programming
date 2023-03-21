package core.di.factory;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;
import core.di.AnnotatedBeanDefinitionReader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AnnotationConfigApplicationContext implements ApplicationContext {
    private final BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        final var basePackages = findBasePackages(annotatedClasses);
        beanFactory = new BeanFactory();
        final var annotatedBeanDefinitionReader = new AnnotatedBeanDefinitionReader(beanFactory);
        annotatedBeanDefinitionReader.register(annotatedClasses);

        if (basePackages.length > 0) {
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
            scanner.doScan(basePackages);
        }
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
                log.info("Component Scan basePackage : {}", basePackage);
            }
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
