package matches.organizer.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserTest {

    @Test
    void passwordIsHashed() {
        String password = "secretpassword";
        User user = new User("alias", "fullName","0303456", "pp@g.com", password);
        assertNotEquals(password, user.getPassword());
    }

    @Test
    void authenticatePassword() {
        String password = "secretpassword";
        User user = new User("alias", "fullName","0303456", "pp@g.com", password);
        assertTrue(user.authenticate(password));
    }

    @Test
    void notAuthenticateWrongPassword() {
        String password = "secretpassword";
        String wrongPassword = "secretpasword";
        User user = new User("alias", "fullName","0303456", "pp@g.com", password);
        assertFalse(user.authenticate(wrongPassword));
    }
}
