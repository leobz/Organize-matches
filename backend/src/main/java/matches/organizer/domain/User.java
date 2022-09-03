package matches.organizer.domain;

import java.util.UUID;

public class User {
    private UUID id = UUID.randomUUID();
    private String alias;
    private String fullName;
    private String phone;
    private String email;
    private String password;

    public User(String alias, String fullName, String password) {
        this.alias = alias;
        this.fullName = fullName;
        // TODO: Hashear password
        this.password = password;
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
}
