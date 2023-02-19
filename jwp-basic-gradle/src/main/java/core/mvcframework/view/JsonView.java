package core.mvcframework.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void render(final Map<String, Object> model, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(objectMapper.writeValueAsString(model));
    }
}
