package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;

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
    public void inject(final Class<?> clazz) {
        Class<?> concreteClass = findConcreteClass(clazz, getPreInstantiatedBeans());
        if (getBean(concreteClass) == null) {
            instantiateClass(concreteClass);
        }
        Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
        for (Field injectedField : injectedFields) {
            Class<?> fieldConcreteClass = findConcreteClass(injectedField.getType(), getPreInstantiatedBeans());
            Object bean = getBean(fieldConcreteClass);

            if (bean == null) {
                bean = instantiateClass(fieldConcreteClass);
            }
            injectedField.setAccessible(true);
            try {
                injectedField.set(getBean(injectedField.getDeclaringClass()), bean);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

}
