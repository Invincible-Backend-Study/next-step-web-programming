package core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RawMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;

}
