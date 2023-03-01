package core.ref;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;
import next.qna.domain.Question;
import next.user.entity.User;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.info(clazz.getName());
        logger.info(clazz.getSimpleName());

        logger.info("============ fields ===========");
        for (var field : clazz.getDeclaredFields()) {
            final var nameOfField = field.getName();
            final var typeOfField = field.getType();
            logger.info("{} {}", typeOfField, nameOfField);
        }

        logger.info("============ constructors =====");
        for (var constructors : clazz.getConstructors()) {
            final var modifierType = Modifier.toString(constructors.getModifiers()); // 생성자의 타입
            final var parameterCountByConstructors = constructors.getParameterCount();
            final var name = constructors.getName();

            logger.info("{} {} 파라미터의 개수:{}", modifierType, name, parameterCountByConstructors);
        }

        logger.info("============ method ===========");

        for (var methods : clazz.getDeclaredMethods()) {
            final var methodsModifier = Modifier.toString(methods.getModifiers());
            final var methodsName = methods.getName();
            final var methodsReturnType = methods.getReturnType();
            final var methodsParameter = methods.getParameters();

            final var parameters = String.format("(%s)",
                    Arrays.stream(methodsParameter).map(parameter -> String.format("%s %s", parameter.getType(), parameter.getName()))
                            .collect(Collectors.joining(",")));

            logger.info("{} {} {} {} ", methodsModifier, methodsReturnType, methodsName, parameters);

        }

    }


    @Test
    public void newInstanceWithConstructorArgs()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;

        //String userId, String password, String name, String email
        final var user = clazz.getDeclaredConstructor(String.class, String.class, String.class, String.class)
                .newInstance("id1", "password", "name", "email");

        logger.info("{}", user);

        logger.debug(clazz.getName());
    }

    @Test
    public void privateFieldAccess()
            throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Student> clazz = Student.class;

        final var student = clazz.getConstructor().newInstance();
        final var field = clazz.getDeclaredField("name");

        field.setAccessible(true);
        field.set(student, "주한");

        logger.debug(student.getName());
        logger.debug(clazz.getName());
    }
}
