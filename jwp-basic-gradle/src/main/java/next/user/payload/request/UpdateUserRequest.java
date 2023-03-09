package next.user.payload.request;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String userId;
    private String password;
    private String name;
    private String email;
}
