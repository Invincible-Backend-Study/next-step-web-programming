package com.jwp.inbound.user.port.driving.dto;


import com.jwp.inbound.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadOnlyUser {
    private final String userId;
    private final String name;
    private final String email;

    public static ReadOnlyUser of(String userId, String name, String email) {
        return new ReadOnlyUser(userId, name, email);
    }

    public static ReadOnlyUser of(User user) {
        return ReadOnlyUser.of(user.getUserId(), user.getName(), user.getEmail());
    }
}
