package core.di.factory;

import core.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;

import java.lang.reflect.Method;
import java.util.Set;

public class AnnotatedBeanDefinitionReader {
    private static final Logger log = LoggerFactory.getLogger(AnnotatedBeanDefinitionReader.class);

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    private void registerBean(Class<?> annotatedClass) {
        beanDefinitionRegistry.registerBeanDefinition(annotatedClass, new BeanDefinition(annotatedClass));
        Set<Method> beanMethods = BeanFactoryUtils.getBeanMethods(annotatedClass, Bean.class);
        for (Method beanMethod : beanMethods) {
            log.debug("@Bean method : {}", beanMethod);
            AnnotatedBeanDefinition abd = new AnnotatedBeanDefinition(beanMethod.getReturnType(), beanMethod);
            beanDefinitionRegistry.registerBeanDefinition(beanMethod.getReturnType(), abd);
        }
    }
}
