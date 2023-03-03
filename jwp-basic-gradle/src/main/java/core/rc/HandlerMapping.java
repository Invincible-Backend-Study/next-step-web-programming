package core.rc;

import javax.servlet.http.HttpServletRequest;

public interface HandlerMapping {
    Object getHandler(HttpServletRequest request);
    
}
