package matches.organizer.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.UUID;

public class User {
    private UUID id = UUID.randomUUID();
    private String alias;
    private String fullName;
    private String phone;
    private String email;
    private String password;

    public User(String alias, String fullName, String password) {
        setAlias(alias);
        setFullName(fullName);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) { this.alias = alias; }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public String getPassword() { return password; }

    public boolean authenticate(String password) {
        return new BCryptPasswordEncoder().matches(password, this.getPassword());
    }
}
