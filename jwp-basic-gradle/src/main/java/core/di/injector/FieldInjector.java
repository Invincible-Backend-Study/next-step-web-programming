package core.di.injector;

import static core.di.BeanFactoryUtils.findConcreteClass;
import static core.di.BeanFactoryUtils.getInjectedConstructor;

import core.di.BeanFactory;
import core.di.BeanFactoryUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class FieldInjector implements Injector {

    private static final Logger log = LoggerFactory.getLogger(FieldInjector.class);

    private final BeanFactory beanFactory;

    public FieldInjector(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(final Class<?> clazz) {
        Class<?> concreteClass = findConcreteClass(clazz, beanFactory.getPreInstantiatedBeans());
        if (beanFactory.getBean(concreteClass) == null) {
            instantiateClass(concreteClass);
        }
        Set<Field> injectedFields = BeanFactoryUtils.getInjectedFields(clazz);
        for (Field injectedField : injectedFields) {
            Class<?> fieldConcreteClass = findConcreteClass(injectedField.getType(), beanFactory.getPreInstantiatedBeans());
            Object bean = beanFactory.getBean(fieldConcreteClass);

            if (bean == null) {
                bean = instantiateClass(fieldConcreteClass);
            }
            injectedField.setAccessible(true);
            try {
                injectedField.set(beanFactory.getBean(injectedField.getDeclaringClass()), bean);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private Object instantiateClass(final Class<?> preInstantiatedBean) {
        Constructor<?> injectedConstructor = getInjectedConstructor(preInstantiatedBean);
        if (injectedConstructor == null) {
            Object bean = BeanUtils.instantiateClass(preInstantiatedBean);
            beanFactory.registerBean(preInstantiatedBean, bean);
            return bean;
        }
        Object bean = instantiateParameterConstructor(injectedConstructor);
        beanFactory.registerBean(preInstantiatedBean, bean);
        return bean;
    }

    private Object instantiateParameterConstructor(final Constructor<?> injectedConstructor) {
        Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
        instantiateParameter(parameterTypes);
        return BeanUtils.instantiateClass(injectedConstructor, findBeans(parameterTypes));
    }

    private void instantiateParameter(final Class<?>[] parameterTypes) {
        for (Class<?> parameterType : parameterTypes) {
            Class<?> concreteClass = findConcreteClass(parameterType, beanFactory.getPreInstantiatedBeans());
            if (beanFactory.getBean(concreteClass) == null) {
                instantiateClass(concreteClass);
            }
        }
    }

    private Object[] findBeans(final Class<?>[] parameterTypes) {
        return Arrays.stream(parameterTypes)
                .map(type -> findConcreteClass(type, beanFactory.getPreInstantiatedBeans()))
                .map(beanFactory::getBean)
                .toArray();
    }

}
