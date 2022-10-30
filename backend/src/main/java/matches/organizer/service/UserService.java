package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.User;
import matches.organizer.storage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public User getUser(String userId) {
        return  userRepository.findById(userId).orElse(null);

    }

    public User addUser(User newUser) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(newUser);

        if (violations.isEmpty()) {
            validateNewUser(newUser);
            userRepository.save(newUser);
            logger.info("USER WITH ID: {} CREATED CORRECTLY", newUser.getId());
            return newUser;
        } else {
            for (ConstraintViolation<User> violation : violations) {
                logger.info(violation.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválidos");
        }
    }

    private void validateNewUser(User newUser){
        if(newUser.getAlias() == null || Objects.equals(newUser.getAlias(), "")){
            logger.error("CANNOT CREATE USER WITHOUT ALIAS");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Alias is missing");
        }
        if(newUser.getFullName() == null || Objects.equals(newUser.getFullName(), "")){
            logger.error("CANNOT CREATE USER WITHOUT FULL NAME");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Full Name is missing");
        }
        if(newUser.getPhone() == null || Objects.equals(newUser.getPhone(), "")){
            logger.error("CANNOT CREATE USER WITHOUT PHONE");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is missing");
        }
        if(newUser.getEmail() == null || Objects.equals(newUser.getEmail(), "")){
            logger.error("CANNOT CREATE USER WITHOUT EMAIL");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is missing");
        }
        if(newUser.getPassword() == null || Objects.equals(newUser.getPassword(), "")){
            logger.error("CANNOT CREATE USER WITHOUT PASSWORD");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is missing");
        }
        if(userRepository.findByEmail(newUser.getEmail()) != null) {
            logger.error("CANNOT REGISTER USER. EMAIL ALREADY EXISTS.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot register. Email already exists.");
        }
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
