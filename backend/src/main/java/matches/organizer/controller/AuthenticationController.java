package matches.organizer.controller;


import com.google.gson.JsonObject;
import matches.organizer.domain.User;
import matches.organizer.dto.AuthenticationDTO;
import matches.organizer.service.AuthenticationService;
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

@RestController
@EnableWebMvc
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final JwtUtils jwtUtils;


    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtUtils jwtUtils) {
        this.authenticationService = authenticationService;
        this.jwtUtils = jwtUtils;
    }

    Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping(value= "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody AuthenticationDTO userTryingLogin, HttpServletResponse response) {
        logger.info("POST TO: /login");
        String token = authenticationService.loginUser(userTryingLogin);//<-- si el usuario no existe, tirás el 401 desde acá

        Cookie cookie = authenticationService.createSecureCookie("token", token,60 * 60);

        // agrego cookie al response
        response.addCookie(cookie);

        JsonObject loginResponse = new JsonObject();
        User user = authenticationService.getUserByEmail(userTryingLogin.getEmail());
        loginResponse.addProperty("userId",user.getId());
        loginResponse.addProperty("tokenExpirationDate", jwtUtils.getTokenExpirationDate(token).toString());
        return loginResponse.toString();
    }


    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String logout(@CookieValue(value = "token", defaultValue = "") String auth, HttpServletResponse response) throws Exception {
        logger.info("POST TO: /logout with token: {}", auth);
        if(!auth.isEmpty()) {
            jwtUtils.addTokenToBlacklist(auth);
        }

        // crea la cookie token con maxAge 0, para forzar el borrado
        Cookie cookie = authenticationService.createSecureCookie("token", auth,0);

        response.addCookie(cookie);

        JsonObject loginResponse = new JsonObject();
        loginResponse.addProperty("response","You have successfully logged out.");
        return  loginResponse.toString();
    }





}