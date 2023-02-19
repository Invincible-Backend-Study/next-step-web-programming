package core.jdbc;

import com.fasterxml.jackson.core.JsonProcessingException;
import core.mvc.ModelAndView;
import core.mvc.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@FunctionalInterface
public interface Controller {
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
