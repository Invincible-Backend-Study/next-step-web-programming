package next.user.service;

import core.annotation.Service;
import lombok.Getter;
import next.user.dao.UserDao;
import next.user.entity.User;
import next.user.payload.request.UpdateUserRequest;


@Service
@Getter
public class UpdateUserService {

    private final UserDao userDao = UserDao.getInstance();

    public static UpdateUserService getInstance() {
        return UpdateUserServiceHolder.UPDATE_USER_SERVICE;
    }

    public User execute(UpdateUserRequest updateUserRequest) {
        final var user = userDao.findByUserId(updateUserRequest.getUserId());
        user.updateUserInformation(
                updateUserRequest.getPassword(),
                updateUserRequest.getName(),
                updateUserRequest.getEmail()
        );
        userDao.update(user);
        return user;
    }

    private static class UpdateUserServiceHolder {
        private static final UpdateUserService UPDATE_USER_SERVICE = new UpdateUserService();
    }
}
