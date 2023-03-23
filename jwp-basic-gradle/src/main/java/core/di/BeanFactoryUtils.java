package core.di;

import static org.reflections.ReflectionUtils.getAllConstructors;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.withAnnotation;

import com.google.common.collect.Sets;
import core.annotation.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanFactoryUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanFactoryUtils.class);

    /**
     * 인자로 전달하는 클래스의 생성자 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @param clazz
     * @return
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));
        if (injectedConstructors.size() > 1) {
            throw new IllegalStateException("@Inject는 하나의 생성자에서만 사용할 수 있습니다.");
        }
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }

    /**
     * 인자로 전달하는 클래스의 필드 중에서 @Inject 애노테이션이 설정되어 있는 필드를 반환
     *
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Set<Field> getInjectedFields(final Class<?> clazz) {
        return getAllFields(clazz, withAnnotation(Inject.class));
    }

    @SuppressWarnings("unchecked")
    public static Set<Method> getInjectedMethods(final Class<?> clazz) {
        return ReflectionUtils.getAllMethods(clazz, withAnnotation(Inject.class));
    }

    /**
     * 인자로 전달되는 클래스의 구현 클래스. 만약 인자로 전달되는 Class가 인터페이스가 아니면 전달되는 인자가 구현 클래스, 인터페이스인 경우 BeanFactory가 관리하는 모든 클래스 중에 인터페이스를
     * 구현하는 클래스를 찾아 반환
     *
     * @param injectedClazz
     * @param preInstanticateBeans
     * @return
     */
    public static Optional<Class<?>> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstanticateBeans) {
        if (!injectedClazz.isInterface()) {
            return Optional.of(injectedClazz);
        }

        for (Class<?> clazz : preInstanticateBeans) {
            Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
            if (interfaces.contains(injectedClazz)) {
                return Optional.of(clazz);
            }
        }

        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    public static Set<Method> getBeanMethods(final Class<?> annotatedClass,
                                             final Class<? extends Annotation> beanClass) {
        return ReflectionUtils.getAllMethods(annotatedClass, withAnnotation(beanClass));
    }

    public static Optional<Object> invokeMethod(final Method method, final Object bean, final Object[] args) {
        try {
            return Optional.ofNullable(method.invoke(bean, args));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
