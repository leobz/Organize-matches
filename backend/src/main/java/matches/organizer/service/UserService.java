package matches.organizer.service;

import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public JwtUtils getJwtUtils() {
        return jwtUtils;
    }

    @Autowired
    private JwtUtils jwtUtils;

    public User createUser(User newUser) {
        User user = new User(newUser.getAlias(), newUser.getFullName(), newUser.getPhone(), newUser.getEmail(), newUser.getPassword());
        userRepository.add(user);
        logger.info("USER WITH ID: {} CREATED CORRECTLY", user.getId());
        return user;
    }

    public User getUser(UUID userId) {
        return  userRepository.get(userId);

    }

    public User addUser(User newUser) {
        validateNewUser(newUser);
        userRepository.add(newUser);
        logger.info("USER WITH ID: " + newUser.getId().toString() + " CREATED CORRECTLY");
        return newUser;
    }

    private void validateNewUser(User newUser){
        if(newUser.getAlias() == null || newUser.getAlias() == ""){
            logger.error("CANNOT CREATE USER WITHOUT ALIAS");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alias is missing");
        }
        if(newUser.getFullName() == null || newUser.getFullName() == ""){
            logger.error("CANNOT CREATE USER WITHOUT FULL NAME");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full Name is missing");
        }
        if(newUser.getPhone() == null || newUser.getPhone() == ""){
            logger.error("CANNOT CREATE USER WITHOUT PHONE");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is missing");
        }
        if(newUser.getEmail() == null || newUser.getEmail() == ""){
            logger.error("CANNOT CREATE USER WITHOUT EMAIL");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is missing");
        }
        if(newUser.getPassword() == null || newUser.getPassword() == ""){
            logger.error("CANNOT CREATE USER WITHOUT PASSWORD");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is missing");
        }
        if(userRepository.getByEmail(newUser.getEmail()) != null) {
            logger.error("CANNOT REGISTER USER. EMAIL ALREADY EXISTS.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot register. Email already exists.");
        }
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }
    
}
