package db;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;
import model.User;

public class DataBase {
    private static Map<String, User> users = Maps.newHashMap();

    static {
        users.put("test", new User("test", "1234", "test", "test@none.com"));
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
