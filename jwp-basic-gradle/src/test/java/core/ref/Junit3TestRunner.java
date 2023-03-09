package core.ref;


import java.lang.reflect.Method;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;

        final var methodExecutor = new ArrayList<Method>();
        for (final var method : clazz.getDeclaredMethods()) {
            if (method.getName().startsWith("test")) {
                method.invoke(clazz.getDeclaredConstructor().newInstance());
            }
        }

    }
}
