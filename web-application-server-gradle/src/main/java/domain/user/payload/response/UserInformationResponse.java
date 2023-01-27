package domain.user.payload.response;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInformationResponse {
    private String userId;
    private String name;
    private String email;

    public String toHTML() {
        return String.format("<li><span>사용자 id: %s</span><span>사용자 이름: %s</span><span>사용자 이메일: %s</span></li>", userId, name, email);
    }
}
