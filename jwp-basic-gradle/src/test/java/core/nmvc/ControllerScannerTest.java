//package core.nmvc;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import core.di.BeanFactory;
//import core.di.ClasspathBeanDefinitionScanner;
//import java.util.Map;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//public class ControllerScannerTest {
//
//    ClasspathBeanDefinitionScanner classpathBeanDefinitionScanner;
//
//    @BeforeEach
//    void setup() {
//        classpathBeanDefinitionScanner = new ClasspathBeanDefinitionScanner("core.nmvc");
//    }
//
//    @Test
//    @DisplayName("MyController 스캔 테스트")
//    void getControllers() {
// when
//        BeanFactory beanFactory = new BeanFactory(classpathBeanDefinitionScanner.scan());
//        beanFactory.initialize();
//        Map<Class<?>, Object> controllers = beanFactory.getControllers();
//
// then
//        Assertions.assertAll(
//                () -> assertThat(controllers.size()).isEqualTo(1),
//                () -> assertThat(
//                        controllers.get(MyController.class).toString().contains("core.nmvc.MyController")).isTrue()
//        );
//
//    }
//}