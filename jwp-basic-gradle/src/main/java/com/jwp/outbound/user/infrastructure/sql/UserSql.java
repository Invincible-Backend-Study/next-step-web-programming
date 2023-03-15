package com.jwp.outbound.user.infrastructure.sql;

public class UserSql {

    public static final String CREATE = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

    public static final String FIND_USER_BY_ID = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

    public static final String FIND_ALL = "SELECT userId, password, name, email FROM USERS";

    public static final String UPDATE = "UPDATE USERS SET "
            + "password = ?,"
            + "name = ?,"
            + "email = ? "
            + "WHERE userid = ?";
    public static final String FIND_USER_BY_NAME = "SELECT userId, password, name, email From USERS WHERE userid=?";
}
