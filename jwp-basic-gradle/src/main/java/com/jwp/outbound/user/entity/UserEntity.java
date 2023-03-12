package com.jwp.outbound.user.entity;


import com.jwp.inbound.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserEntity {
    private String userId;
    private String password;
    private String name;
    private String email;

    public static UserEntity of(String userId, String password, String name, String email) {
        return new UserEntity(userId, password, name, email);
    }

    public User toUser() {
        return User.of(userId, password, name, email);
    }
}
