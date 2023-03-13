package next.user.service;

import com.jwp.outbound.user.infrastructure.UserDao;
import core.annotation.Service;
import lombok.Getter;
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
//        user.updateUserInformation(
//                updateUserRequest.getPassword(),
//                updateUserRequest.getName(),
//                updateUserRequest.getEmail()
//        );
//        userDao.update(user);
        return null;
    }

    private static class UpdateUserServiceHolder {
        private static final UpdateUserService UPDATE_USER_SERVICE = new UpdateUserService();
    }
}
