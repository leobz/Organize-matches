package matches.organizer.controller;


import com.google.gson.JsonObject;
import matches.organizer.domain.User;
import matches.organizer.dto.AuthenticationDTO;
import matches.organizer.service.AuthenticationService;
import matches.organizer.service.MatchService;
import matches.organizer.service.UserService;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableWebMvc
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Autowired
    private JwtUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

/*
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody LoginUserDTO loginUser(@RequestBody LoginUserDTO newLogin) throws Exception{

        authenticationService.loginUser(newLogin);
    return newLogin;
    }
 */


    @GetMapping(value= "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody AuthenticationDTO userTryingLogin, HttpServletResponse response) {
        logger.info("THE USER WITH MAIL: " + userTryingLogin.getEmail() + " AND PASSWORD: " + userTryingLogin.getPassword() + " IS TRYING TO LOGIN");


        //User user = userService.getUserByEmail(userToLogin.getEmail()); //<-- yo te creo este método en mi branch 39-sign-in
        //if (user == null )
        //   logger.error("CANNOT MAPPED THE EMAIL WITH AN EXISTING USER");

        String token = authenticationService.loginUser(userTryingLogin);//<-- si el usuario no existe, tirás el 401 desde acá

        // crea una cookie
        Cookie cookie = new Cookie("token",token);
        // expira en 1 hora
        cookie.setMaxAge(60 * 60);
        // para seguridad
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        // agrego cookie al response
        response.addCookie(cookie);

        JsonObject loginResponse = new JsonObject();
        User user = authenticationService.getUserByEmail(userTryingLogin.getEmail());
        loginResponse.addProperty("userId",user.getId().toString());
        return loginResponse.toString();
    }

    /*
    @GetMapping(value = "/privateApi", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Object privateApi(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {

        jwtUtils.verify(auth);
        return  authenticationService.getUsers();
    }

     */


}