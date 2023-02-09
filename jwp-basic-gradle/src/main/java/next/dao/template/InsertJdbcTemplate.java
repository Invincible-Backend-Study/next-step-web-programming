package next.dao.template;

import core.jdbc.ConnectionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import next.dao.UserDao;
import next.model.User;

public class InsertJdbcTemplate {
    public void insert(User user, UserDao userDao) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = ConnectionManager.getConnection();
            final var sql = userDao.createQueryForInsert();
            preparedStatement = con.prepareStatement(sql);
            userDao.setValuesForInsert(user,preparedStatement);
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
