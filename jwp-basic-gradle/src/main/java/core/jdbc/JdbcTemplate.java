package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;

public abstract class JdbcTemplate {
    public void update(final String query) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValue(pstmt);
            pstmt.executeUpdate();
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public List query(final String query) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            Statement st = con.createStatement();
            rs = st.executeQuery(query);

            List<Object> results = new ArrayList<>();
            while (rs.next()) {
                results.add(mapRow(rs));
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
            return results;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public Object queryForObject(final String query) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValue(pstmt);
            rs = pstmt.executeQuery();

            Object result = null;
            if (rs.next()) {
                result = mapRow(rs);
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

            return result;
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    protected abstract void setValue(final PreparedStatement preparedStatement) throws SQLException;

    protected abstract Object mapRow(final ResultSet resultSet) throws SQLException;

}