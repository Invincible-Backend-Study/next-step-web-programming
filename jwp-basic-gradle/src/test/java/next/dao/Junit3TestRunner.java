package next.dao;

import next.Junit3Test;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit3TestRunner {
    @Test
    public void run() {
        Class<Junit3Test> testClass = Junit3Test.class;
        Arrays.stream(testClass.getMethods())
                .forEach(method -> {
                    try {
                        if (method.isAnnotationPresent(Test.class)) {
                            method.invoke(testClass.getConstructor().newInstance());
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
