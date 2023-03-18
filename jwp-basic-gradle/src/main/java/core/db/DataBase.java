package core.db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import next.user.entity.User;

public class DataBase {
    private static final Map<String, User> users = Maps.newHashMap();

    static {
        addUser(User.of("123", "123", "123", "123"));
    }

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
