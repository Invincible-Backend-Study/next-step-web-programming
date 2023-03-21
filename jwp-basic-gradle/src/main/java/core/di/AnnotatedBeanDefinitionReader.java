package core.di;

import core.annotation.Bean;
import core.di.factory.BeanDefinition;
import core.di.factory.BeanDefinitionRegistry;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class AnnotatedBeanDefinitionReader {
    private final BeanDefinitionRegistry beanDefinitionRegistry;

    public void register(Class<?>... annotatedClasses) {
        Arrays.stream(annotatedClasses).forEach(this::registerBean);
    }

    private void registerBean(Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
        BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class)
                .forEach(beanMethod -> registerBeanAnnotationMethod(annotatedClass, beanMethod));
    }

    private void registerBeanAnnotationMethod(Class<?> annotatedClass, Method beanMethod) {
        log.debug("@Bean method:{}", beanMethod);
        final var annotatedBeanDefinition = new AnnotatedBeanDefinition(annotatedClass, beanMethod);
        beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), annotatedBeanDefinition);
    }
}
