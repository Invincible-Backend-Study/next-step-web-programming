package core.di.factory.inject;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractInjector {
    private static final Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(BeanFactory beanFactory, Set<Class<?>> preInstanticateBeans) {
        super(beanFactory, preInstanticateBeans);
    }

    @Override
    Constructor<?> getInjectedConstructor(Field field) {
        return BeanFactoryUtils.getInjectedConstructor(field);
    }
}
