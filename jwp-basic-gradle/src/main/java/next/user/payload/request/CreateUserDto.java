package next.user.payload.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import next.model.User;


@Getter
@RequiredArgsConstructor
public class CreateUserDto {
    private final String userId;
    private final String password;
    private final String name;
    private final String email;

    public User toEntity() {
        return User.of(userId, password, name, email);
    }
}
