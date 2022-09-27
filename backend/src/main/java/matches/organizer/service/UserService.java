package matches.organizer.service;

import matches.organizer.domain.User;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public JwtUtils jwtUtils;

    public User createUser(User newUser) {
        User user = new User(newUser.getAlias(), newUser.getFullName(), newUser.getPhone(), newUser.getEmail(), newUser.getPassword());
        userRepository.add(user);
        logger.info("USER WITH ID: {} CREATED CORRECTLY", user.getId());
        return user;
    }

    public User addUser(User newUser) {
        userRepository.add(newUser);
        logger.info("USER WITH ID: " + newUser.getId().toString() + " CREATED CORRECTLY");
        return newUser;
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }
    
}
