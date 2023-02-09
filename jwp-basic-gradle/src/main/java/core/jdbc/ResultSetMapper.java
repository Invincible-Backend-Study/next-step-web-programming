package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetMapper {
    Object mapRow(ResultSet rs) throws SQLException;
}
