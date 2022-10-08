package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.User;
import matches.organizer.service.UserService;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {UserService.class, UserController.class, JwtUtils.class})
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void createUser() throws Exception {
        this.mvc.perform(post( "/users").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\n" +
            "    \"alias\": \"pepito\",\n" +
            "    \"phone\": \"123456\",\n" +
            "    \"email\": \"pepito@gmail.com\",\n" +
            "    \"fullName\": \"Pepito Pepitez\",\n" +
            "    \"password\": \"pepito123\"\n" +
            "}").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isCreated());
    }

    @Test
    void getUsers() throws Exception {
        User user1 = buildNewUser();
        User user2 = buildNewUser();

        ArrayList<User> users = new ArrayList<>();

        users.add(user1);
        users.add(user2);

        when(userRepository.findAll()).thenReturn(users);

        this.mvc.perform(get("/users")
                        .cookie(new Cookie("token",jwtUtils.generateJwt(user1)))
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

    private User buildNewUser() {
        return new User("pepito", "Pepito Pepitez", "0303456", "pp@g.com", "pepito123");
    }

}
