package core.ref;

import org.junit.jupiter.api.Test;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        final var instance = clazz.getDeclaredConstructor().newInstance();

        for (final var method : clazz.getDeclaredMethods()) {
            if (method.getDeclaredAnnotation(MyTest.class) == null) {
                continue;
            }

            method.invoke(instance);
        }
    }
}
