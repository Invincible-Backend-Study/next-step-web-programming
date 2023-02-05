package core.web.controller;

import java.util.HashMap;
import java.util.Map;
import next.web.controller.CreateUserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger log = LoggerFactory.getLogger(RequestMapping.class);
    private static final Map<String, Controller> handlerMapping = new HashMap<>();

    static {
        handlerMapping.put("/user/create", new CreateUserController());
    }

    public Controller getHandlerMapping(final String requestURI) {
        log.debug("requestURI={}", requestURI);
        return handlerMapping.get(requestURI);
    }
}
