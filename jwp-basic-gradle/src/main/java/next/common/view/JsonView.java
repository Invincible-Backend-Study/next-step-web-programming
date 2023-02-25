package next.common.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.web.View;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonView implements View {
    private static final Logger log = LoggerFactory.getLogger(JsonView.class);
    // private final Object object;

//    public JsonView(Object object) {
//        this.object = object;
//    }

    @Override
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // log.debug("JsonView Model: {}", model.toString());

        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(model));
    }
}
