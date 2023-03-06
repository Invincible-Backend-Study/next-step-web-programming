package next.nmvc.mappging;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import next.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerExecution {
    private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);

    private Object declaredObject;
    private Method method;

    public HandlerExecution(Object declaredObject, Method method){
        this.declaredObject =  declaredObject;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest req, HttpServletResponse res) throws Exception{
        try {
          return (ModelAndView) method.invoke(declaredObject,req,res);
        }catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e){
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
