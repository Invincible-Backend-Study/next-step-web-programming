import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class GenericTest {
    class A {
        @Override
        public String toString() {
            return "A class";
        }
    }

    class B {
        @Override
        public String toString() {
            return "A class";
        }
    }

    @Test
    void throwException_noGeneric() {
        // given
        List list = new ArrayList();

        // when
        list.add(new A());
        list.add(new A());
        list.add(new A());
        list.add(new B());

        // then 런타임 오류 발생
        Assertions.assertThatThrownBy(() -> {
                    for (Object a : list) {
                        A castA = (A) a;
                        System.out.println(castA);
                    }
                }
        ).isInstanceOf(ClassCastException.class);
    }

    @Test
    void noException_withGeneric() {
        // given
        List<A> list = new ArrayList();

        // when
        list.add(new A());
        list.add(new A());
        list.add(new A());
        // list.add(new B());  // 컴파일 타임 오류 발생!!!

        // then
        Assertions.assertThatNoException()
                .isThrownBy(() -> {
                    for (Object a : list) {
                        A castA = (A) a;
                        System.out.println(castA);

                    }
                });
    }
}

