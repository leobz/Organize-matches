package matches.organizer.storage;

import matches.organizer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User anyUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        anyUser = buildAUser();
        anotherUser = buildAnotherUser();
    }

    @Test
    void addAndGetUser() {
        userRepository.save(anyUser);
        userRepository.save(anotherUser);
        Assertions.assertEquals(anyUser.getId(),userRepository.findById(anyUser.getId()).orElse(new User()).getId());
        Assertions.assertEquals(anotherUser.getId(),userRepository.findById(anotherUser.getId()).orElse(new User()).getId());
    }

    @Test
    void addAndGetUserByEmail() {
        userRepository.save(anyUser);
        Assertions.assertEquals(anyUser.getEmail(),userRepository.findByEmail(anyUser.getEmail()).getEmail());
    }

    @Test
    void addAndRemoveUser() {
        userRepository.save(anyUser);
        userRepository.save(anotherUser);
        userRepository.delete(anyUser);
        Assertions.assertNull(userRepository.findById(anyUser.getId()).orElse(new User()).getId());
        Assertions.assertEquals(anotherUser.getId(),userRepository.findById(anotherUser.getId()).orElse(new User()).getId());
    }

    @Test
    void addAndUpdateUser() {
        userRepository.save(anyUser);
        Assertions.assertEquals("Any User",userRepository.findById(anyUser.getId()).orElse(new User()).getAlias());
        anyUser.setAlias("Modified User");
        userRepository.save(anyUser);
        Assertions.assertNotEquals("Any User", userRepository.findById(anyUser.getId()).orElse(new User()).getAlias());
        Assertions.assertEquals("Modified User", userRepository.findById(anyUser.getId()).orElse(new User()).getAlias());
    }

    @Test
    void updateUnknownUser() {
        User anyUser = buildAUser();
        userRepository.save(anyUser);
        userRepository.save(buildAnotherUser());
        Assertions.assertNull(userRepository.findById(anotherUser.getId()).orElse(null));
        Assertions.assertEquals(anyUser.getId(),userRepository.findById(anyUser.getId()).orElse(new User()).getId());
    }

    private User buildAUser() {
        return new User("Any User", "Any User fullname", "0303456", "pp@g.com", "anypassword");
    }

    private User buildAnotherUser() {
        return new User("Another User", "Another User fullname", "0303456", "pp2@g.com", "another-password");
    }
}
