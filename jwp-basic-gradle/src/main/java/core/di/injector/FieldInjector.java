package core.di.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import java.lang.reflect.Field;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class FieldInjector extends AbstractInjector {

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public Set<?> getInjectedBeans(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    Class<?> getBeanClass(Object injectedBean) {
        final var field = (Field) injectedBean;
        return field.getType();
    }

    @Override
    void inject(Object injectedBean, Object bean, BeanFactory beanFactory) {
        Field field = (Field) injectedBean;
        try {
            field.setAccessible(true);
            field.set(beanFactory.getBean(field.getDeclaringClass()), bean);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }
}
