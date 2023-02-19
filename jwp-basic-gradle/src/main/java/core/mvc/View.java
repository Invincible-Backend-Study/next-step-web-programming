package core.mvc;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
    void render(Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception;
}
