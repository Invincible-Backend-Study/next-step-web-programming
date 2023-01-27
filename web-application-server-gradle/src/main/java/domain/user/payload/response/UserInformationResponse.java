package domain.user.payload.response;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserInformationResponse {
    private String userId;
    private String name;
    private String email;

    public String toHTML() {
        return String.format("<li><span>%s</span><span>%s</span><span>%s</span></li>", userId, name, email);
    }
}
