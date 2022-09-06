package matches.organizer.dto;

import matches.organizer.domain.User;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String alias;
    private String fullName;
    private String phone;
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.alias = user.getAlias();
        this.fullName = user.getFullName();
        this.phone = user.getPhone();
        this.email = user.getEmail();
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

    public String getEmail() {
        return email;
    }
}
