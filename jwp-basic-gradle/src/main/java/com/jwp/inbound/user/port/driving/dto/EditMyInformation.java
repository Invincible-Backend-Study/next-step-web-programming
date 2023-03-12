package com.jwp.inbound.user.port.driving.dto;

import com.jwp.inbound.user.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EditMyInformation {
    private final String userId;
    private final String name;

    private final String password;
    private final String email;

    public static EditMyInformation of(String userId, String name, String password, String email) {
        return new EditMyInformation(userId, name, password, email);
    }

    public static EditMyInformation from(User user) {
        return new EditMyInformation(user.getUserId(), user.getName(), user.getPassword(), user.getEmail());
    }
}
