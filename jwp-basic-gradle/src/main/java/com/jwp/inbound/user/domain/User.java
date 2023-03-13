package com.jwp.inbound.user.domain;

import com.jwp.inbound.user.domain.policy.UserPolicy;
import com.jwp.outbound.user.entity.UserEntity;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@EqualsAndHashCode
@Getter
public class User {
    private final String userId;
    private String password;
    private String name;
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        UserPolicy.validate(this);
    }

    public static User of(String userId, String password, String name, String email) {
        return new User(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void updateUserInformation(String password, String name, String email) {
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public boolean comparePassword(String password) {
        return Objects.equals(this.password, password);
    }

    public UserEntity toEntity() {
        return UserEntity.of(userId, password, name, email);
    }
}
