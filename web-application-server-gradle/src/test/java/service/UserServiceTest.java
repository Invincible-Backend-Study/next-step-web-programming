package service;

import static org.assertj.core.api.Assertions.assertThat;

import db.DataBase;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UserServiceTest {

    @ParameterizedTest
    @CsvSource({"test,1234,test,test@nene.com", "test2,1234,test,test@nene.com", "test3,1234,test,test@nene.com"})
    @DisplayName("회원정보 저장 성공 테스트")
    void addUser(String userId, String password, String name, String email) {
        // given
        User addUser = new User(userId, password, name, email);

        // when
        DataBase.addUser(addUser);
        User findUser = DataBase.findUserById(addUser.getUserId());

        // then
        assertThat(addUser.getUserId()).isEqualTo(findUser.getUserId());
    }
}