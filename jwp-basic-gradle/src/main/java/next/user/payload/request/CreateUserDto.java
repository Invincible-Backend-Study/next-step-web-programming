package next.user.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import next.user.entity.User;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String userId;
    private String password;
    private String name;
    private String email;

    public User toEntity() {
        return User.of(userId, password, name, email);
    }
}
