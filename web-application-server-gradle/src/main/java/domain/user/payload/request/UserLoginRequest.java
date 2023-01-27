package domain.user.payload.request;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private final String userId;
    private final String password;

    private UserLoginRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public static UserLoginRequest of(String userId, String password) {
        return new UserLoginRequest(userId, password);
    }
}
