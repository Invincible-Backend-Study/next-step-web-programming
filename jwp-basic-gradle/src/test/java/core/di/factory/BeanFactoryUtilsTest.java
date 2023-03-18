package core.di.factory;

import core.di.factory.example.ExampleFieldClass;
import core.di.factory.example.ExampleMethodClass;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BeanFactoryUtilsTest {
    @Test
    void getInjectedInClass() {
        //Constructor<?> constructor = BeanFactoryUtils.getInjectedInClass(ExampleMethodClass.class);
        //System.out.println(constructor.toString());
    }

    @Test
    void getInjectedConstructor() {
    }

    @Test
    void getInjectedField() {
        Field field = BeanFactoryUtils.getInjectedField(ExampleFieldClass.class);
        assertEquals("one", field.getName());
    }

    @Test
    void getInjectedMethod() {
        Method method = BeanFactoryUtils.getInjectedMethod(ExampleMethodClass.class);
        assertEquals("setOne", method.getName());
        method.getParameterTypes();
    }
}