package next.common.controller;


import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SampleController {

    @RequestMapping(method = RequestMethod.GET, value = "/sample")
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JsonView()).addObject("test", "1");
    }
}
