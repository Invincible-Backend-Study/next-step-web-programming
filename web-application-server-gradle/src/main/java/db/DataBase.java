package db;

import com.google.common.collect.Maps;
import domain.user.entity.User;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class DataBase {
    private static final Map<String, User> users = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static Optional<User> findUserById(String userId) {
        return Optional.ofNullable(users.getOrDefault(userId, null));
    }

    public static boolean existsUserById(final String id) {
        return users.containsKey(id);
    }

    public static Collection<User> findAll() {
        return users.values();
    }

}
