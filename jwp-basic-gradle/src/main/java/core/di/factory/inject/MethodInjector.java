package core.di.factory.inject;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

@Deprecated
public class MethodInjector extends AbstractInjector {
    public MethodInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedMethods(clazz);
    }

    Class<?> getBeanClass(Object injectedBean) {
        Method method = (Method) injectedBean;
        // logger.debug("invoke method : {}", method);
        Class<?>[] paramTypes = method.getParameterTypes();
        if (paramTypes.length != 1) {
            throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
        }

        return paramTypes[0];
    }

    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Method method = (Method) injectedBean;
        try {
            method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            // logger.error(e.getMessage());
        } catch (InvocationTargetException e) {
            // throw new RuntimeException(e);
        }
    }
}
