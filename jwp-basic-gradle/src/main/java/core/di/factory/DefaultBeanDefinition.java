package core.di.factory;

import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;

public class DefaultBeanDefinition implements core.di.factory.config.BeanDefinition {

    private final Class<?> beanClazz;
    private Constructor<?> injectConstructor;
    private Set<Field> injectFields;

    public DefaultBeanDefinition(final Class<?> beanClazz) {
        this.beanClazz = beanClazz;
        injectConstructor = getInjectConstructor(beanClazz);
        injectFields = getInjectFields(beanClazz, injectConstructor);

    }

    private Constructor<?> getInjectConstructor(final Class<?> beanClazz) {
        return BeanFactoryUtils.getInjectedConstructor(beanClazz);
    }

    private Set<Field> getInjectFields(final Class<?> beanClazz, final Constructor<?> constructor) {
        if (constructor != null) {
            return Sets.newHashSet();
        }
        Set<Field> injectFields = Sets.newHashSet();
        Set<Class<?>> injectProperties = getInjectPropertiesType(beanClazz);
        Field[] fields = beanClazz.getDeclaredFields();
        for (Field field : fields) {
            if (injectProperties.contains(field.getType())) {
                injectFields.add(field);
            }
        }
        return injectFields;
    }

    private Set<Class<?>> getInjectPropertiesType(final Class<?> beanClazz) {
        Set<Class<?>> injectProperties = Sets.newHashSet();
        // setter inject 조회
        for (Method injectedMethod : BeanFactoryUtils.getInjectedMethods(beanClazz)) {
            Class<?>[] parameterTypes = injectedMethod.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalStateException("DI할 메소드 인자는 하나여야 합니다.");
            }
            injectProperties.add(parameterTypes[0]);
        }
        // field inject 조회
        for (Field injectedField : BeanFactoryUtils.getInjectedFields(beanClazz)) {
            injectProperties.add(injectedField.getType());
        }
        return injectProperties;
    }

    @Override
    public Constructor<?> getInjectConstructor() {
        return injectConstructor;
    }

    @Override
    public Set<Field> getInjectFields() {
        return Collections.unmodifiableSet(injectFields);
    }

    @Override
    public InjectType getResolvedInjectMode() {
        if (injectConstructor != null) {
            return InjectType.INJECT_CONSTRUCTOR;
        }
        if (!injectFields.isEmpty()) {
            return InjectType.INJECT_FIELD;
        }
        return InjectType.INJECT_NO;
    }

    @Override
    public Class<?> getBeanClass() {
        return beanClazz;
    }
}
