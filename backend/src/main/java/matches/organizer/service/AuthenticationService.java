package matches.organizer.service;

import matches.organizer.domain.User;
import matches.organizer.dto.AuthenticationDTO;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;

@Service
public class AuthenticationService{

    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);


    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationService(UserRepository userRepository, JwtUtils jwtUtils)  {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public String loginUser(AuthenticationDTO userTryingLogin) {

        User user = userRepository.findByEmail(userTryingLogin.getEmail());
        if (user == null ){
            logger.info("CANNOT MAP THE EMAIL WITH AN EXISTING USER");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

        if(user.authenticate(userTryingLogin.getPassword())){
            String token = jwtUtils.generateJwt(user);
            logger.info("TOKEN GENERATED: {}", token );
            return token;
        }else {
            logger.info("USER LOGIN FAILED: INCORRECT PASSWORD");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied.");
        }

    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Cookie createSecureCookie(String name, String value, int maxAge) {
        // crea una cookie
        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(maxAge);

        // para seguridad
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }


}