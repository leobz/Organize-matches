package matches.organizer.storage;

import matches.organizer.domain.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public User get(UUID id) {
        return users.stream()
                .filter(user -> hasSameId(id, user))
                .findFirst().orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    @Override
    public void update(User user) {
        if (users.removeIf(anyUser -> hasSameId(anyUser.getId(), user))) {
            add(user);
        }
    }

    @Override
    public void remove(User user) {
        users.removeIf(anyUser -> hasSameId(anyUser.getId(), user));
    }

    private static boolean hasSameId(UUID anyUserId, User user) {
        return anyUserId.equals(user.getId());
    }
}
