package com.jwp.outbound.user.adapter.driving.request;

import com.jwp.inbound.user.domain.User;
import lombok.Getter;


@Getter
public class CreateUserRequest {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User toUser() {
        return User.of(userId, password, name, email);
    }
}
