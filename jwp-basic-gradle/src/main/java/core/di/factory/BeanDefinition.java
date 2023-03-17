package core.di.factory;

import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import lombok.Getter;


@Getter
public class BeanDefinition {
    private final Class<?> beanClazz;
    private final Constructor<?> injectConstructor;
    private final Set<Field> injectedFields;

    public BeanDefinition(Class<?> clazz) {
        this.beanClazz = clazz;
        injectConstructor = getInjectedConstructor(clazz);
        injectedFields = getInjectFields(clazz, injectConstructor);
    }

    private static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        return BeanFactoryUtils.getInjectedConstructor(clazz);
    }

    private static Set<Class<?>> getInjectPropertiesType(Class<?> clazz) {
        Set<Class<?>> injectProperties = Sets.newHashSet();
        final var injectMethod = BeanFactoryUtils.getInjectedMethods(clazz);
        for (final var method : injectMethod) {
            final var paramTypes = method.getParameterTypes();
            if (paramTypes.length != 1) {
                throw new IllegalStateException("DI할 메서드 인자는 하나여야 합니다.");
            }

            injectProperties.add(paramTypes[0]);
        }

        Set<Field> injectField = BeanFactoryUtils.getInjectedFields(clazz);
        for (final var field : injectField) {
            injectProperties.add(field.getType());
        }
        return injectProperties;
    }


    private Set<Field> getInjectFields(Class<?> clazz, Constructor<?> constructor) {
        if (constructor != null) {
            return Sets.newHashSet();
        }

        Set<Field> injectFields = Sets.newHashSet();
        Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);

        for (final var field : clazz.getDeclaredFields()) {
            if (injectProperties.contains(field.getType())) {
                injectFields.add(field);
            }
        }
        return injectFields;
    }

    
}
