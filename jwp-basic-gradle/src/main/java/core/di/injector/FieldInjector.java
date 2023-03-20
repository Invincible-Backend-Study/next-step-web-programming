package core.di.injector;

import core.di.BeanFactory;
import core.di.BeanFactoryUtils;
import java.lang.reflect.Field;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldInjector extends AbstractInjector {

    private static final Logger log = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(final BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected Set<?> getInjectedBeans(final Class<?> clazz) {
        return BeanFactoryUtils.getInjectedFields(clazz);
    }

    @Override
    protected Class<?> getBeanClass(final Object injectedBean) {
        Field injectedField = (Field) injectedBean;
        return injectedField.getType();
    }

    @Override
    protected void inject(final Object injectedBean, final Object bean, final BeanFactory beanFactory) {
        Field injectedField = (Field) injectedBean;
        injectedField.setAccessible(true);
        try {
            injectedField.set(beanFactory.getBean(injectedField.getDeclaringClass()), bean);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
