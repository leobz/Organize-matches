package matches.organizer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.dto.UserDTO;
import matches.organizer.service.UserService;
import matches.organizer.storage.InMemoryUserRepository;
import matches.organizer.storage.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {UserService.class, UserController.class, InMemoryUserRepository.class})
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser() throws Exception {
        this.mvc.perform(post( "/users").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
            "    \"alias\": \"pepito\",\n" +
            "    \"fullName\": \"Pepito Pepitez\",\n" +
            "    \"password\": \"pepito123\"\n" +
            "}").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
    }

    @Test
    public void getUsers() throws Exception {
        sanitize();
        User user1 = addNewUser();
        User user2 = addNewUser();

        ArrayList<UserDTO> users = new ArrayList<UserDTO>();

        users.add(user1.getDto());
        users.add(user2.getDto());

        Gson gson = new Gson();

        this.mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(gson.toJson(users)));
    }

    private User addNewUser() {
        User user = new User("pepito", "Pepito Pepitez", "pepito123");
        userRepository.add(user);
        return user;
    }

    void sanitize() {
        userRepository.getAll().clear();
    }

}
