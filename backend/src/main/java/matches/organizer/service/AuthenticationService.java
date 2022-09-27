package matches.organizer.service;

import matches.organizer.domain.User;
import matches.organizer.dto.AuthenticationDTO;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthenticationService{

    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    /*
    public JwtUtils getJwtUtils() {
        return jwtUtils;
    }

     */

    @Autowired
    public JwtUtils jwtUtils;

    @Autowired
    public AuthenticationService(UserService userService, UserRepository userRepository)  {
        this.userService = userService;
        this.userRepository = userRepository;
    }





    public String loginUser(AuthenticationDTO userTryingLogin) {

        User user = userRepository.getUserByEmail(userTryingLogin.getEmail()); //<-- yo te creo este método en mi branch 39-sign-in
        if (user == null ){
            logger.info("CANNOT MAP THE EMAIL WITH AN EXISTING USER");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

        if(user.authenticate(userTryingLogin.getPassword())){
            String token = jwtUtils.generateJwt(user);
            logger.info("TOKEN GENERATED: " + token);
            return token;
        }else {
            logger.info("USER LOGIN FAILED: INCORRECT PASSWORD");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

    }


    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }





}