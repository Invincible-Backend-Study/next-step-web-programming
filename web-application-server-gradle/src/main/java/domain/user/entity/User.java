package domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;


public class User {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;

        ValidateUserPolicy.validate(this);
    }

    public static User of(String userId, String password, String name, String email) {
        return User.builder()
                .userId(userId)
                .name(name)
                .password(password)
                .email(email)
                .build();
    }


    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + "]";
    }

    public boolean comparePassword(String password) {
        return this.password.equals(password);
    }
}
