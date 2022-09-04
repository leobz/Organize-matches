package matches.organizer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class User {
    private UUID id = UUID.randomUUID();
    private String alias;
    private String fullName;
    private String phone;
    private String email;
    private String password;

    @JsonCreator
    public User(@JsonProperty("alias") String alias) {
        this.alias = alias;
    }

    public User(@JsonProperty("alias") String alias, String fullName, String password) {
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

    public String getFullName() {
        return fullName;
    }

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
