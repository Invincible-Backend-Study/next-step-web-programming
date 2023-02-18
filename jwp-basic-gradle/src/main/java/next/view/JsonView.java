package next.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.web.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonView implements View {
    private final Object object;

    public JsonView(Object object) {
        this.object = object;
    }

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(object));
    }
}
