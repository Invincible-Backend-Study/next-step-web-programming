package core.web.mvcframework.mapping;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {

    Object getHandler(final HttpServletRequest request);

    void initialize();

}
