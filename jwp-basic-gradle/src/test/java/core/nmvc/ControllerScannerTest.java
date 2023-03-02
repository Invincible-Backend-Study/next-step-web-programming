package core.nmvc;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControllerScannerTest {

    @Test
    void init() throws Exception {
        ControllerScanner cs = new ControllerScanner();
        Map<Class<?>, Object> controllers = cs.getControllers("next.api");
        System.out.println(controllers.toString());
    }
}