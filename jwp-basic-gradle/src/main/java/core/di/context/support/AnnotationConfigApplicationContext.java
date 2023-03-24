package core.di.context.support;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;
import core.di.beans.factory.support.DefaultBeanFactory;
import core.di.context.ApplicationContext;
import core.di.context.annotation.AnnotatedBeanDefinitionReader;
import core.di.context.annotation.ClasspathBeanDefinitionScanner;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationConfigApplicationContext implements ApplicationContext {

    private static final Logger log = LoggerFactory.getLogger(AnnotationConfigApplicationContext.class);

    private final DefaultBeanFactory defaultBeanFactory;

    public AnnotationConfigApplicationContext(final Class<?>... configurationClasses) {
        Object[] basePackages = findBasePackages(configurationClasses);
        defaultBeanFactory = new DefaultBeanFactory();
        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(defaultBeanFactory);
        reader.loadBeanDefinitions(configurationClasses);

        if (basePackages.length > 0) {
            ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(defaultBeanFactory);
            scanner.doScan(basePackages);
        }
        defaultBeanFactory.preInstantiateSingletons();
    }

    private Object[] findBasePackages(final Class<?>[] annotatedClasses) {
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
    public Set<Class<?>> getBeanClasses() {
        return defaultBeanFactory.getBeanClasses();
    }

    @Override
    public <T> T getBean(final Class<T> beanClass) {
        return defaultBeanFactory.getBean(beanClass);
    }
}
