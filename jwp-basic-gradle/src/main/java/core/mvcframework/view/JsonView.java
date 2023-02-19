package core.mvcframework.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        Map<String, Object> model = createModel(request);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(model));
    }

    private Map<String, Object> createModel(final HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            model.put(attributeName, request.getAttribute(attributeName));
        }
        log.debug("model={}", model);
        return model;
    }
}
