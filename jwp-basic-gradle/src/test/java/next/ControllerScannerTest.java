package next;

import next.nmvc.ControllerScanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ControllerScannerTest {

    private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);
    private ControllerScanner cf;

    @BeforeEach
    public void setUp() {
        cf = new ControllerScanner("next");
    }

    @Test
    void getControllers() {
        Map<Class<?>, Object> controllers = cf.getController();
        for (Class<?> controller : controllers.keySet()) {
            logger.debug("{}",controller);
        }
    }
}
