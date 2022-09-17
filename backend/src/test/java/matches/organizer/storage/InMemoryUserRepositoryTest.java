package matches.organizer.storage;

import matches.organizer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class InMemoryUserRepositoryTest {

    private UserRepository userRepository;
    private User anyUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        anyUser = buildAUser();
        anotherUser = buildAnotherUser();
    }

    @Test
    void addAndGetUser() {
        userRepository.add(anyUser);
        userRepository.add(anotherUser);
        Assertions.assertEquals(anyUser.getId(),userRepository.get(anyUser.getId()).getId());
        Assertions.assertEquals(anotherUser.getId(),userRepository.get(anotherUser.getId()).getId());
    }

    @Test
    void addAndRemoveUser() {
        userRepository.add(anyUser);
        userRepository.add(anotherUser);
        userRepository.remove(anyUser);
        Assertions.assertNull(userRepository.get(anyUser.getId()));
        Assertions.assertEquals(anotherUser.getId(),userRepository.get(anotherUser.getId()).getId());
    }

    @Test
    void addAndUpdateUser() {
        userRepository.add(anyUser);
        Assertions.assertEquals("Any User",userRepository.get(anyUser.getId()).getAlias());
        anyUser.setAlias("Modified User");
        userRepository.update(anyUser);
        Assertions.assertNotEquals("Any User", userRepository.get(anyUser.getId()).getAlias());
        Assertions.assertEquals("Modified User", userRepository.get(anyUser.getId()).getAlias());
    }

    @Test
    void updateUnknownUser() {
        User anyUser = buildAUser();
        userRepository.add(anyUser);
        userRepository.update(buildAnotherUser());
        Assertions.assertNull(userRepository.get(anotherUser.getId()));
        Assertions.assertEquals(anyUser.getId(),userRepository.get(anyUser.getId()).getId());
    }

    private User buildAUser() {
        return new User("Any User", "Any User fullname", "0303456", "pp@g.com", "anypassword");
    }

    private User buildAnotherUser() {
        return new User("Another User", "Another User fullname", "0303456", "pp@g.com", "another-password");
    }
}
