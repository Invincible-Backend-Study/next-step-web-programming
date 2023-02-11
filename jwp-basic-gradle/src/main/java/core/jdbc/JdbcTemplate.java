package core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import next.exception.DataAccessException;

public class JdbcTemplate {
    public void update(final String query, final PreparedStatementSetter preparedStatementSetter) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            preparedStatementSetter.setValue(pstmt);
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

    public List query(final String query, final RowMapper rowMapper) {
        Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            Statement st = con.createStatement();
            rs = st.executeQuery(query);

            List<Object> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
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

    public Object queryForObject(
            final String query,
            final PreparedStatementSetter preparedStatementSetter,
            final RowMapper rowMapper) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            preparedStatementSetter.setValue(pstmt);
            rs = pstmt.executeQuery();

            Object result = null;
            if (rs.next()) {
                result = rowMapper.mapRow(rs);
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

}