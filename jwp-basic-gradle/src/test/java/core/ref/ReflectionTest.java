package core.ref;

import next.api.qna.model.Question;
import next.api.user.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClassInformation() {
        Class<Question> clazz = Question.class;
        assertAll(() -> {
            assertEquals("next.api.qna.model.Question", clazz.getName());
            assertTrue(Arrays.toString(clazz.getFields()).equals("[]"));  // public field
        });
        getClassInform(clazz);
    }

    private static void getClassInform(Class<?> clazz) {
        logger.debug(clazz.getName());  // next.api.qna.model.Question
        logger.debug(Arrays.toString(clazz.getFields()));  // public field
        logger.debug(Arrays.toString(clazz.getDeclaredFields()));  // private field
        logger.debug(Arrays.toString(clazz.getConstructors()));
        logger.debug(Arrays.toString(clazz.getMethods()));
    }

    @Test
    public void runTestMethod() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Junit3Test> clazz = Junit3Test.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.invoke(clazz.newInstance());
            }
        }
    }

    @Test
    public void runMyTestAnnotation() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<Junit4Test> clazz = Junit4Test.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.invoke(clazz.newInstance());
            }
        }
    }

    @Test
    public void newInstanceWithConstructorArgs() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class<User> clazz = User.class;
        Constructor constructor = clazz.getConstructor(String.class, String.class, String.class, String.class);
        User user = (User) constructor.newInstance("tid", "tpw", "tname", "t@email.com");

        assertAll(() -> {
            assertEquals("tid", user.getUserId());
            assertEquals("tpw", user.getPassword());
            assertEquals("tname", user.getName());
            assertEquals("t@email.com", user.getEmail());
        });
    }

    @Test
    public void privateFieldAccess() throws IllegalAccessException {
        Class<Student> clazz = Student.class;
        Student student = new Student();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);  // 해당 private field에 접근 가능하게 설정
            if (field.getName().equals("name")) {
                field.set(student, "홍길동");
            }
            if (field.getName().equals("age")) {
                field.setInt(student, 22);
            }
        }

        assertAll(() -> {
            assertEquals("홍길동", student.getName());
            assertEquals(22, student.getAge());
        });
    }

}
