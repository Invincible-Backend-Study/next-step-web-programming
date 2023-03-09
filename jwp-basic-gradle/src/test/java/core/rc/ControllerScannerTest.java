package core.rc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class ControllerScannerTest {
    private static final Logger log = LoggerFactory.getLogger(ControllerScannerTest.class);

    private ControllerScanner controllerScanner;

    @BeforeEach
    void setUp() {
        controllerScanner = new ControllerScanner("core.rc");
    }

    @Test
    void 테스트() {
        final var controllers = controllerScanner.getControllers();
        for (Class<?> controller : controllers.keySet()) {
            log.debug("controller {}", controller);

        }

    }

}