package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.User;
import matches.organizer.service.UserService;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@EnableWebMvc
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String createUser(@RequestBody User newUser) {
        logger.info("POST TO: /users ");
        return userService.addUser(newUser).toJsonString();
    }


    @GetMapping()
    public String getUsers(@CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("GET TO: /users ");
        userService.jwtUtils.verify(auth);
        JsonObject allUsers = new JsonObject();
        JsonArray userArray = new JsonArray();
        userService.getUsers().forEach(user -> userArray.add(JsonParser.parseString(user.toJsonString())));
        allUsers.add("users", userArray);
        return allUsers.toString();
    }

    
/*
    @GetMapping(value = "/private")
    public String getUsersPrivate(@RequestHeader(value = "authorization", defaultValue = "") String auth) throws Exception {
        logger.info("GET TO: /private ");
        userService.getJwtUtils().verify(auth);
        JsonObject allUsers = new JsonObject();
        JsonArray userArray = new JsonArray();
        userService.getUsers().forEach(user -> userArray.add(JsonParser.parseString(user.toJsonString())));
        allUsers.add("users", userArray);
        return allUsers.toString();
    }


 */


}
