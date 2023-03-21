package core.di;

import com.google.common.collect.Lists;
import core.annotation.ComponentScan;
import core.di.factory.BeanFactory;
import core.di.factory.ClasspathBeanDefinitionScanner;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


@Slf4j
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


    private Object[] findBasePackages(Class<?>[] annotatedClasses) {
        final var basePackages = Lists.newArrayList();
        Arrays.stream(annotatedClasses).forEach(annotatedClass -> {
            final var componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            if (componentScan == null) {
                return;
            }
            for (final var basePackage : componentScan.value()) {
                log.info("Component scan basePackage: {}", basePackage);
            }
            basePackages.addAll(List.of(componentScan.value()));
        });
        return basePackages.toArray();
    }
}
