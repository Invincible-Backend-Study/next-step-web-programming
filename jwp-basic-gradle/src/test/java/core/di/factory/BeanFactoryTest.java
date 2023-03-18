package core.di.factory;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.reflections.ReflectionUtils.getAllFields;
import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;

import com.google.common.collect.Sets;
import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.BeanFactory;
import core.di.BeanScanner;
import core.di.factory.example.MyNewQnaService;
import core.di.factory.example.MyQnaService;
import core.di.factory.example.NewQnaController;
import core.di.factory.example.QnaController;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.BeanUtils;

public class BeanFactoryTest {
    private Reflections reflections;
    private BeanFactory beanFactory;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() {
        reflections = new Reflections("core.di.factory.example");
        Set<Class<?>> preInstanticateClazz = getTypesAnnotatedWith(Controller.class, Service.class, Repository.class);
        beanFactory = new BeanFactory(preInstanticateClazz);
        beanFactory.initialize();
    }

    @Test
    public void di() throws Exception {
        QnaController qnaController = beanFactory.getBean(QnaController.class);
        NewQnaController newQnaController = beanFactory.getBean(NewQnaController.class);

        assertNotNull(newQnaController);
        assertNotNull(newQnaController.getMyNewQnaService());

        assertNotNull(qnaController);
        assertNotNull(qnaController.getQnaService());

        MyQnaService qnaService = qnaController.getQnaService();
        MyNewQnaService myNewQnaService = newQnaController.getMyNewQnaService();

        assertNotNull(myNewQnaService.getUserRepository());
        assertNotNull(myNewQnaService.getQuestionRepository());

        assertNotNull(qnaService.getUserRepository());
        assertNotNull(qnaService.getQuestionRepository());
    }

    @SuppressWarnings("unchecked")
    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> beans = Sets.newHashSet();
        for (Class<? extends Annotation> annotation : annotations) {
            beans.addAll(reflections.getTypesAnnotatedWith(annotation));
        }
        return beans;
    }

//    @Test
//    @DisplayName("Field injection test")
//    void injectField() throws IllegalAccessException, InstantiationException, InvocationTargetException {
//        BeanScanner beanScanner = new BeanScanner("core.di.factory.example2");
//        Object instance = null;
//        Set<Class<?>> scan = beanScanner.scan();
//        for (Class<?> clazz : scan) {
//            Set<Field> fields = getAllFields(clazz, withAnnotation(Inject.class));
//            Constructor<?>[] constructors = clazz.getConstructors();
//            for (Constructor<?> constructor : constructors) {
//                if (constructor.getName().endsWith("DummyWithField")) {
//                    System.out.println(constructor);
//                    instance = BeanUtils.instantiate(clazz);
//                }
//            }
//            for (Field field : fields) {
//                System.out.println(field);
//                field.setAccessible(true);
//                field.set(instance, "hi this is field inject");
//            }
//        }
//
//        System.out.println(instance);
//    }
//
//    @Test
//    @DisplayName("Setter injection test")
//    void injectSetter()
//            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
//        BeanScanner beanScanner = new BeanScanner("core.di.factory");
//        Object instance = null;
//        Set<Class<?>> scan = beanScanner.scan();
//
//        for (Class<?> clazz : scan) {
//            if (clazz.getName().endsWith("DummyWithSetter")) {
//                Set<Method> methods = getAllMethods(clazz, withAnnotation(Inject.class));
//                Constructor<?> constructor = clazz.getDeclaredConstructor();
//                instance = constructor.newInstance();
//                for (Method method : methods) {
//                    if (method != null) {
//                        method.invoke(instance, "hi this is setter inject");
//                    }
//                }
//            }
//        }
//        System.out.println(instance);
//
//        Class<?> aClass = instance.getClass();
//        Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
//        for (Constructor<?> declaredConstructor : declaredConstructors) {
//            System.out.println(declaredConstructor);
//        }
//    }
}
