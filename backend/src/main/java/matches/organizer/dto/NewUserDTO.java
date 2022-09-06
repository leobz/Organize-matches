package matches.organizer.dto;

import matches.organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class NewUserDTO {

    @Autowired
    private UserService userService;

    private String alias;
    private String fullName;
    private String phone;
    private String email;

    private String password;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) throws Exception {
        this.alias = alias;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
