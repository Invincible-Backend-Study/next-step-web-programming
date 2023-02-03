package next.web.service;

import core.db.DataBase;
import next.model.User;

public class UserService {

    public void updateUserInformation(final User updatedUser) {
        DataBase.deleteUserById(updatedUser.getUserId());
        DataBase.addUser(updatedUser);
    }
}
