package next.nmvc;

import core.annotation.Controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionUtilTest {
    @Test
    void test() throws IOException, ClassNotFoundException {
        List<Class<?>> clazz = ReflectionUtil.getAnnotatedClasses("next.nmvc", Controller.class);
        Assertions.assertEquals(clazz, List.of(MyController.class));
    }

}