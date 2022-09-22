package matches.organizer.storage;

import matches.organizer.domain.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    User get(UUID id);
    List<User> getAll();
    void add(User user);
    void update(User user);
    void remove(User user);
    User getUserByEmail(String mail);
}
