package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.Sets;

import core.annotation.Inject;
import org.reflections.ReflectionUtils;

import static org.reflections.ReflectionUtils.*;

public class BeanFactoryUtils {
//    @SuppressWarnings({ "rawtypes", "unchecked" })
//    public static Constructor<?> getInjectedInClass(Class<?> clazz) {
//        //TODO refactor
//        Constructor constructor;
//        constructor = getInjectedConstructor(clazz);
//        if (constructor != null) {
//            return constructor;
//        }
//        constructor = getInjectedConstructor(getInjectedField(clazz));
//        if (constructor != null) {
//            return constructor;
//        }
//        return getInjectedConstructor(getInjectedMethod(clazz));
//    }

    /**
     * 인자로 전달하는 클래스의 생성자 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Constructor<?> getInjectedConstructor(Class<?> clazz) {
        System.out.println("Name: " + clazz.getName());
        Set<Constructor> injectedConstructors = getAllConstructors(clazz, withAnnotation(Inject.class));

        System.out.println("Const: " + injectedConstructors.toString());
        if (injectedConstructors.isEmpty()) {
            return null;
        }
        return injectedConstructors.iterator().next();
    }

//    public static Constructor<?> getInjectedConstructor(Field field) {
//        if (field == null) {
//            return null;
//        }
//        return getInjectedConstructor(field.getType());
//    }
//
//    public static Constructor<?> getInjectedConstructor(Method method) {
//        if (method == null) {
//            return null;
//        }
//        Class<?> firstParameterType = method.getParameterTypes()[0];
//        return getInjectedConstructor(firstParameterType);
//    }
    @SuppressWarnings({ "unchecked" })
    public static Set<Method> getInjectedMethods(Class<?> clazz) {
        return getAllMethods(clazz, withAnnotation(Inject.class), withReturnType(void.class));
    }

    @SuppressWarnings({ "unchecked" })
    public static Set<Field> getInjectedFields(Class<?> clazz) {
        return getAllFields(clazz, withAnnotation(Inject.class));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Set<Constructor> getInjectedConstructors(Class<?> clazz) {
        return getAllConstructors(clazz, withAnnotation(Inject.class));
    }

    /**
     * 인자로 전달하는 클래스의 필드 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Field getInjectedField(Class<?> clazz) {
        Set<Field> injectedFields = ReflectionUtils.getAllFields(clazz, withAnnotation(Inject.class));
        if (injectedFields.isEmpty()) {
            return null;
        }
        return injectedFields.iterator().next();
    }

    /**
     * 인자로 전달하는 클래스의 메소드 중 @Inject 애노테이션이 설정되어 있는 생성자를 반환
     *
     * @Inject 애노테이션이 설정되어 있는 생성자는 클래스당 하나로 가정한다.
     * @param clazz
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Method getInjectedMethod(Class<?> clazz) {
        Set<Method> injectedMethods = getAllMethods(clazz, withAnnotation(Inject.class));
        if (injectedMethods.isEmpty()) {
            return null;
        }
        return injectedMethods.iterator().next();
    }

    /**
     * 인자로 전달되는 클래스의 구현 클래스. 만약 인자로 전달되는 Class가 인터페이스가 아니면 전달되는 인자가 구현 클래스,
     * 인터페이스인 경우 BeanFactory가 관리하는 모든 클래스 중에 인터페이스를 구현하는 클래스를 찾아 반환
     * 
     * @param injectedClazz
     * @param preInstanticateBeans
     * @return
     */
    public static Class<?> findConcreteClass(Class<?> injectedClazz, Set<Class<?>> preInstanticateBeans) {
        if (!injectedClazz.isInterface()) {
            return injectedClazz;
        }

        for (Class<?> clazz : preInstanticateBeans) {
            Set<Class<?>> interfaces = Sets.newHashSet(clazz.getInterfaces());
            if (interfaces.contains(injectedClazz)) {
                return clazz;
            }
        }

        throw new IllegalStateException(injectedClazz + "인터페이스를 구현하는 Bean이 존재하지 않는다.");
    }
}
