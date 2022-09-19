package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.User;
import matches.organizer.dto.RegisterUserDTO;
import matches.organizer.service.UserService;
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
    public String createUser(@RequestBody RegisterUserDTO newUser) {
        logger.info("POST TO: /users ");
        return userService.createUser(newUser).toJsonString();
    }

    @GetMapping()
    public String getUsers() {
        logger.info("GET TO: /users ");
        JsonObject allUsers = new JsonObject();
        JsonArray userArray = new JsonArray();
        userService.getUsers().forEach(user -> userArray.add(JsonParser.parseString(user.toJsonString())));
        allUsers.add("users", userArray);
        return allUsers.toString();
    }

}
