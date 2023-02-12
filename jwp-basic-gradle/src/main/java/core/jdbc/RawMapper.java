package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RawMapper {
    Object mapRow(ResultSet rs) throws SQLException;

}
