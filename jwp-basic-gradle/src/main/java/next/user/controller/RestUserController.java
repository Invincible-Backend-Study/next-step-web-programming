package next.user.controller;


import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.rc.RestController;
import java.util.List;
import next.user.entity.User;

@RestController
public class RestUserController {


    @RequestMapping(value = "/rest/user", method = RequestMethod.GET)
    public Integer test() {
        return 1;
    }

    @RequestMapping(value = "/rest/list", method = RequestMethod.GET)
    public List<User> userList() {
        return List.of(User.of("1234", "1234", "1234", "1234"));
    }
}
