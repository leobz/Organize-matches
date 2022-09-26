package matches.organizer.service;

import matches.organizer.domain.User;
import matches.organizer.storage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public User createUser(User newUser) {
        User user = new User(newUser.getAlias(), newUser.getFullName(), newUser.getPhone(), newUser.getEmail(), newUser.getPassword());
        userRepository.add(user);
        logger.info("USER WITH ID: " + user.getId().toString() + " CREATED CORRECTLY");
        return user;
    }

    public User getUser(UUID userId) {
        return  userRepository.get(userId);
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }
}
