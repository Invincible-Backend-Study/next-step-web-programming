package next.dao;

import core.jdbc.ConnectionManager;
import core.jdbc.InsertJdbcTemplate;
import core.jdbc.UpdateJdbcTemplate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;
import next.model.User;

public class UserDao {
    private final InsertJdbcTemplate insertJdbcTemplate = new InsertJdbcTemplate() {
        @Override
        protected void setValueForInsert(final User user, final PreparedStatement pstmt) throws SQLException {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
        }

        @Override
        protected String createQueryForInsert() {
            return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        }
    };

    private final UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate() {
        @Override
        protected void setValueForUpdate(final User updatedUser, final PreparedStatement pstmt) throws SQLException {
            pstmt.setString(1, updatedUser.getPassword());
            pstmt.setString(2, updatedUser.getName());
            pstmt.setString(3, updatedUser.getEmail());
            pstmt.setString(4, updatedUser.getUserId());
        }

        @Override
        protected String createQueryForUpdate() {
            return "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
        }
    };

    public void insert(User user) {
        insertJdbcTemplate.insert(user);
    }

    public void update(final User user) {
        updateJdbcTemplate.update(user);
    }

    public User findByUserId(String userId) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }

            return user;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public List<User> findAll() {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS";
            Statement st = con.createStatement();

            rs = st.executeQuery(sql);

            List<User> users = new ArrayList<>();
            User user;
            while (rs.next()) {
                user = new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                users.add(user);
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
            return users;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
