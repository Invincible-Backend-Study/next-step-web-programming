package domain.user.application;

import db.DataBase;

public class RegisterUserUseCase {

    public void execute() {
        final var bodyParams = request.getParameter();

        // 이미 회원가입한 사용자에 대한 처리
        if (DataBase.existsUserById(bodyParams.get("userId"))) {
            responseRedirectHeader(dataOutputStream, "/user/form.html");
            return;
        }
        // null 혹은 빈값에 대한 예외처리는 하지 않음
        final var user = User.builder()
                .userId(bodyParams.get("userId"))
                .password(bodyParams.get("password"))
                .email(URLDecoder.decode(bodyParams.get("email"), StandardCharsets.UTF_8))
                .name(bodyParams.get("name"))
                .build();

        // 사용자 추가
        DataBase.addUser(user);
        responseRedirectHeader(dataOutputStream, "/index.html");
    }

}
