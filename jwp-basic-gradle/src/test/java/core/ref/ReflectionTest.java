package core.ref;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ReflectionTest {
    @Test
    void test() throws NoSuchFieldException, IllegalAccessException {
        Student student = new Student();
        Field studentField = student.getClass().getDeclaredField("name");
        studentField.setAccessible(true);
        studentField.set(student,"이준호");
        Assertions.assertEquals(student.getName(),"이준호");
    }
}

class Student {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
