package core.ref;


import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Junit4TestRunner {

    @Test
    @DisplayName("리플렉션을 이용해 Junit4Test에서 '@MyTest'애노테이션이 설정되어있는 모든 메소드를 실행")
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(MyTest.class))
                .forEach(method -> {
                    try {
                        method.invoke(clazz.getConstructor().newInstance());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
