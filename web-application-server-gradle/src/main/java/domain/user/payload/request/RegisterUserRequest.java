package domain.user.payload.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RegisterUserRequest {
    private String userId;
    private String password;
    private String name;
    private String email;

    public static RegisterUserRequest of(String userId, String password, String name, String email) {
        return RegisterUserRequest.builder()
                .email(email)
                .userId(userId)
                .name(name)
                .password(password)
                .build();
    }
}
