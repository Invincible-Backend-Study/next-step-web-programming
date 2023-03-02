package core.ref;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import next.model.Question;
import next.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionTest {
    private static final Logger log = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    @DisplayName("리플렉션 이용해 Question 클래스의 필드, 생성자, 메소드 정보 출력 테스트")
    public void showClass() {
        Class<Question> clazz = Question.class;
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> log.debug("filed={}", field));
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(constructor -> log.debug("constructor={}", constructor));
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> log.debug("method={}", method));
        log.debug(clazz.getName());
    }

    @Test
    @DisplayName("리플렉션 이용해 기본 생성자가 없는 클래스의 인스턴스 생성하기")
    public void newInstanceWithConstructorArgs() throws Exception {
        // given
        Class<User> clazz = User.class;

        // when
        Constructor<User> constructor = clazz.getConstructor(String.class, String.class, String.class, String.class);
        User user = constructor.newInstance("hoseok", "1234", "이호석", "test@test.com");
        log.debug("userFromReflection={}", user);

        // then
        Assertions.assertAll(
                () -> assertThat(user.getUserId()).isEqualTo("hoseok"),
                () -> assertThat(user.getPassword()).isEqualTo("1234"),
                () -> assertThat(user.getName()).isEqualTo("이호석"),
                () -> assertThat(user.getEmail()).isEqualTo("test@test.com")
        );
    }

    @Test
    @DisplayName("리플렉션 이용해 private 필드에 값을 할당하기")
    public void privateFieldAccess() throws Exception {
        // given
        Class<Student> clazz = Student.class;
        Field[] fields = clazz.getDeclaredFields();
        Student student = clazz.getConstructor().newInstance();

        // when
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType() == String.class) {
                field.set(student, "이호석");
                continue;
            }
            if (field.getType() == int.class) {
                field.set(student, 26);
            }
        }

        // then
        log.debug("student={}", student);
        Assertions.assertAll(
                () -> assertThat(student.getName()).isEqualTo("이호석"),
                () -> assertThat(student.getAge()).isEqualTo(26)
        );

    }
}
