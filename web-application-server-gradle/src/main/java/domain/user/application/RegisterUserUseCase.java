package domain.user.application;

import db.DataBase;
import domain.common.DomainExceptionCode;
import domain.user.entity.User;
import domain.user.payload.request.RegisterUserRequest;

public class RegisterUserUseCase {

    public void execute(RegisterUserRequest registerUserRequest) {

        // 이미 회원가입한 사용자에 대한 처리
        if (DataBase.existsUserById(registerUserRequest.getUserId())) {
            throw DomainExceptionCode.PREVIOUS_SIGN_UP_USER_ID.createError(registerUserRequest.getUserId(), "/user/form.html");
        }
        // null 혹은 빈값에 대한 예외처리는 하지 않음

        final var user = User.of(
                registerUserRequest.getUserId(),
                registerUserRequest.getPassword(),
                registerUserRequest.getName(),
                registerUserRequest.getEmail()
        );
        // 사용자 추가
        DataBase.addUser(user);
        //responseRedirectHeader(dataOutputStream, "/index.html");
    }

}
