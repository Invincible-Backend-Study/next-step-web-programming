package core.rc;


import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleController {
    @RequestMapping(method = RequestMethod.GET, value = "/sample/1")
    public ModelAndView a() {
        log.debug("findUserId");
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/users")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response) {
        log.debug("save");
        return null;
    }
}
