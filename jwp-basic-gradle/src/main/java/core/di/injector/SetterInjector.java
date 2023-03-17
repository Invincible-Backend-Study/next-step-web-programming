package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetterInjector extends AbstractInjector {
    public SetterInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        final var method = (Method) injectedBean;
        log.debug("invoke method : {}", method);
        Class<?>[] paramTypes = method.getParameterTypes();

        if (paramTypes.length != 1) {
            throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
        }
        return paramTypes[0];
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        final var method = (Method) injectedBean;
        try {
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}
