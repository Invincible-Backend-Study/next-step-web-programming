package domain.user.application;

import db.DataBase;
import domain.user.payload.response.UserInformationResponse;
import java.util.List;
import java.util.stream.Collectors;

public class FindUserUseCase {

    public List<UserInformationResponse> getAllUser() {
        return DataBase.findAll().stream()
                .map(user -> new UserInformationResponse(user.getUserId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }
}
