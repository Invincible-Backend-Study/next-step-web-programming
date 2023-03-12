package com.jwp.outbound.user.adapter.driven;


import com.jwp.inbound.user.domain.User;
import com.jwp.outbound.user.entity.UserEntity;
import core.annotation.Inject;
import core.annotation.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import next.user.repository.dao.UserDao;
import next.user.service.port.UserPort;

@Repository
public class UserAdapter implements UserPort {

    private final UserDao userDao;

    @Inject
    public UserAdapter(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findById(String id) {
        final var user = userDao.findById(id);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(userDao.findByUserId(id).toUser());
    }

    @Override
    public User create(User user) {
        userDao.insert(user.toEntity());
        return userDao.findByUserId(user.getUserId()).toUser();
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll().stream().map(UserEntity::toUser).collect(Collectors.toList());
    }
}
