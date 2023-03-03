package core.nmvc;

import static org.assertj.core.api.Assertions.assertThat;

import core.mvcframework.mapping.annotation.ControllerScanner;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ControllerScannerTest {

    ControllerScanner controllerScanner;

    @BeforeEach
    void setup() {
        controllerScanner = new ControllerScanner("core.nmvc");
    }

    @Test
    @DisplayName("MyController 스캔 테스트")
    void getControllers() {
        // when
        Map<Class<?>, Object> controllers = controllerScanner.getControllers();

        // then
        Assertions.assertAll(
                () -> assertThat(controllers.size()).isEqualTo(1),
                () -> assertThat(controllers.get(MyController.class).toString().contains("core.nmvc.MyController")).isTrue()
        );

    }
}
