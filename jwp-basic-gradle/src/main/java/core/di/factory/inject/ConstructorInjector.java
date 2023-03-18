package core.di.factory.inject;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstructorInjector extends AbstractInjector {
    private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);

    public ConstructorInjector(BeanFactory beanFactory, Set<Class<?>> preInstanticateBeans) {
        super(beanFactory, preInstanticateBeans);
    }

    @Override
    Constructor<?> getInjectedConstructor(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }
}
