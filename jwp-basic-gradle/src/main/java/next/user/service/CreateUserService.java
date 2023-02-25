package next.user.service;

import next.user.dao.UserDao;
import next.user.payload.request.CreateUserDto;

public class CreateUserService {
    private final UserDao userDao = new UserDao();

    public void execute(final CreateUserDto createUserDto) {
        userDao.insert(createUserDto.toEntity());
    }

}
