package core.mvcframework.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonView implements View {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(final HttpServletRequest request, final HttpServletResponse response)
            throws JsonProcessingException {
        Map<String, Object> model = createModel(request);
        response.setContentType("application/json;charset=UTF-8");
        objectMapper.writeValueAsString(model);
    }

    private Map<String, Object> createModel(final HttpServletRequest request) {
        Enumeration<String> attributeNames = request.getAttributeNames();
        Map<String, Object> model = new HashMap<>();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            model.put(attributeName, request.getAttribute(attributeName));
        }
        return model;
    }
}
