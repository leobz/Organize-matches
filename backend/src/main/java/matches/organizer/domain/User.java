package matches.organizer.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import matches.organizer.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class User {
    Logger logger = LoggerFactory.getLogger(User.class);

    @Expose
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;
    @Expose
    private String alias;
    @Expose
    private String fullName;
    @Expose
    private String phone;
    @Expose
    private String email;
    private String password;


    public User(){}

    @JsonCreator
    public User(@JsonProperty("alias") String alias) {
        this.id = UUID.randomUUID();
        this.alias = alias;
    }

    public User(String alias, String fullName, String phone, String email, String password) {
        this.id = UUID.randomUUID();
        setAlias(alias);
        setFullName(fullName);
        setPhone(phone);
        setEmail(email);
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

    public String toJsonString() {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }

}
