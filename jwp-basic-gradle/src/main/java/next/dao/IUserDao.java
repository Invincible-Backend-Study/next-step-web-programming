package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import next.model.User;

public interface IUserDao {
    void insert(User user) throws SQLException;
    void setValuesForInsert(User user , PreparedStatement preparedStatement) throws SQLException;
    String createQueryForInsert();
    void update(User user) throws SQLException;

    void setValuesForUpdate(User user, PreparedStatement preparedStatement) throws SQLException;

    String createQueryForUpdate();

    List<User> findAll() throws SQLException;

    User findByUserId(String userId) throws SQLException;

}
