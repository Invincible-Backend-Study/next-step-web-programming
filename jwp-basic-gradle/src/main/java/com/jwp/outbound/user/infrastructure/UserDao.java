package com.jwp.outbound.user.infrastructure;

import com.jwp.outbound.user.entity.UserEntity;
import com.jwp.outbound.user.infrastructure.sql.UserSql;
import core.annotation.Repository;
import core.jdbc.JdbcTemplate;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class UserDao {
    private final JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public static UserDao getInstance() {
        return UserDaoHolder.USER_DAO;
    }

    public void insert(UserEntity user) {
        jdbcTemplate.update(UserSql.CREATE, (preparedStatement -> {
            preparedStatement.setString(1, user.getUserId());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
        }));
    }

    public UserEntity findByUserId(String userId) {
        return jdbcTemplate.queryForObject(UserSql.FIND_USER_BY_ID, preparedStatement -> preparedStatement.setString(1, userId),
                resultSet -> UserEntity.of(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                )
        );
    }

    public List<UserEntity> findAll() {
        return jdbcTemplate.query(UserSql.FIND_ALL, preparedStatement -> {
                },
                resultSet -> UserEntity.of(
                        resultSet.getString("userId"),
                        resultSet.getString("password"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                )
        );
    }

    public void update(final UserEntity user) {
        jdbcTemplate.update(UserSql.UPDATE, preparedStatement -> {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getUserId());
        });
    }

    public Optional<UserEntity> findById(String userId) {
        return Optional.ofNullable(this.findByUserId(userId));
    }

    public Optional<UserEntity> findByName(String username) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(UserSql.FIND_USER_BY_NAME, preparedStatement -> {
            preparedStatement.setString(1, username);
        }, resultSet -> UserEntity.of(
                resultSet.getString("userId"),
                resultSet.getString("password"),
                resultSet.getString("name"),
                resultSet.getString("email")
        )));
    }

    private static class UserDaoHolder {
        public static final UserDao USER_DAO = new UserDao();
    }
}
