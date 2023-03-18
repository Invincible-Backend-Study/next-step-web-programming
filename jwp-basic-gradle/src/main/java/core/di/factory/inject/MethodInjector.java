package core.di.factory.inject;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;

public class MethodInjector extends AbstractInjector {
    public MethodInjector(BeanFactory beanFactory, Set<Class<?>> preInstanticateBeans) {
        super(beanFactory, preInstanticateBeans);
    }

    @Override
    Constructor<?> getInjectedConstructor(Method method) {
        return BeanFactoryUtils.getInjectedConstructor(method);
    }
}
