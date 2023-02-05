package next.web.controller.dto;

import next.model.User;

public class ProfileUserDto {
    private final String userId;
    private final String name;
    private final String email;

    public ProfileUserDto(final String userId, final String name, final String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public static ProfileUserDto from(final User user) {
        return new ProfileUserDto(user.getUserId(), user.getName(), user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
