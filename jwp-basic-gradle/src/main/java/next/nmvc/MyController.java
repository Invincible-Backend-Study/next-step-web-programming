package next.nmvc;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import next.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyController {

    @RequestMapping("/users/findUserId")
    public ModelAndView findUserId(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }
    @RequestMapping("/users")
    public ModelAndView save(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }
}
