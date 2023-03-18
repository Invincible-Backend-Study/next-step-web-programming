package core.di.factory;

import com.google.common.collect.Sets;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import lombok.Getter;


/**
 * BeanDefinition은 생성자로 전달되는 클래스에서 @Inject가 어떻게 설정되어 있는지에 따라 InjectType을 결정하도록 구현함.
 */

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

    public InjectType getResolvedInjectMode() {
        if (injectConstructor != null) {
            return InjectType.INJECT_CONSTRUCTOR;
        }
        if (!injectedFields.isEmpty()) {
            return InjectType.INJECT_FIELD;
        }
        return InjectType.INJECT_NO;
    }

}
