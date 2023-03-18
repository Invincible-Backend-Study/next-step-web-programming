package next.dao;

import java.util.List;
import next.model.User;

public interface UserDao {

    void insert(User user);

    void update(final User user);

    User findById(String userId);

    List<User> findAll();

}
