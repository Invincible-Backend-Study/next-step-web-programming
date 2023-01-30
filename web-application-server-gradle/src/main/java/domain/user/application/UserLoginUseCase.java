package domain.user.application;

import db.DataBase;
import domain.user.payload.request.UserLoginRequest;

public class UserLoginUseCase {
    public boolean execute(final UserLoginRequest userLoginRequest) {
        return DataBase.findUserById(userLoginRequest.getUserId())
                .map(user -> user.comparePassword(userLoginRequest.getPassword()))
                .orElse(false);
    }
}
