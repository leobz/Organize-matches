package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.User;
import matches.organizer.service.UserService;
import matches.organizer.storage.InMemoryUserRepository;
import matches.organizer.storage.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserService.class, UserController.class, InMemoryUserRepository.class})
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void createUser() throws Exception {
        this.mvc.perform(post( "/users").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
            "    \"alias\": \"pepito\",\n" +
            "    \"fullName\": \"Pepito Pepitez\",\n" +
            "    \"password\": \"pepito123\"\n" +
            "}").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
    }

    @Test
    void getUsers() throws Exception {
        sanitize();
        User user1 = addNewUser();
        User user2 = addNewUser();

        ArrayList<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        this.mvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(getUsersResponse(users)));
    }

    private String getUsersResponse(ArrayList<User> users) {
        JsonObject allUsers = new JsonObject();
        JsonArray userArray = new JsonArray();
        users.forEach(user -> userArray.add(JsonParser.parseString(user.toJsonString())));
        allUsers.add("users", userArray);
        return allUsers.toString();
    }

    private User addNewUser() {
        User user = new User("pepito", "Pepito Pepitez", "0303456", "pp@g.com", "pepito123");
        userRepository.add(user);
        return user;
    }

    void sanitize() {
        userRepository.getAll().clear();
    }

}
