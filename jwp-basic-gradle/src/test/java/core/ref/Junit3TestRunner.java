package core.ref;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Junit3TestRunner {

    private static final Logger log = LoggerFactory.getLogger(Junit3TestRunner.class);

    @Test
    @DisplayName("리플렉션을 이용해 Junit3Test에서 'test'로 시작하는 메소드를 가진 테스트를 실행")
    public void run() {
        Class<Junit3Test> clazz = Junit3Test.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().startsWith("test"))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.getConstructor().newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
