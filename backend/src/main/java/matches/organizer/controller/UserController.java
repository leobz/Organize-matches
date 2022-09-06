package matches.organizer.controller;

import matches.organizer.dto.NewUserDTO;
import matches.organizer.dto.UserDTO;
import matches.organizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableWebMvc
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody UserDTO createUser(@RequestBody NewUserDTO newUserDTO) {
        return userService.createUser(newUserDTO).getDto();
    }

    @GetMapping()
    public @ResponseBody List<UserDTO> getUsers() {
        return userService.getUsers().stream().map(user -> user.getDto()).collect(Collectors.toList());
    }

}
