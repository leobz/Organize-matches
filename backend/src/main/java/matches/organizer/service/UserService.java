package matches.organizer.service;

import matches.organizer.domain.User;
import matches.organizer.dto.RegisterUserDTO;
import matches.organizer.storage.UserRepository;
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

    public User createUser(RegisterUserDTO newUser) {
        User user = new User(newUser.getAlias(), newUser.getFullName(), newUser.getPhone(), newUser.getEmail(), newUser.getPassword());
        userRepository.add(user);
        return user;
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }
}
