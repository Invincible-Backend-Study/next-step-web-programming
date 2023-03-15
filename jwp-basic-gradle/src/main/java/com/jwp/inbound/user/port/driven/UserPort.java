package com.jwp.inbound.user.port.driven;

import com.jwp.inbound.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserPort {
    Optional<User> findById(final String id);

    User create(User user);

    User update(User user);

    List<User> findAll();
}
