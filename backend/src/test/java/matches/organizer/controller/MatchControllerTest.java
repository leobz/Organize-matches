package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.Match;
import matches.organizer.domain.User;
import matches.organizer.service.MatchService;
import matches.organizer.service.UserService;
import matches.organizer.storage.MatchRepository;
import matches.organizer.storage.PlayerRepository;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {JwtUtils.class, MatchService.class, UserService.class, MatchController.class})
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private MatchRepository matchRepository;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;
    @Test
    void matchesRetrieved() throws Exception {
        User user = createUser();
        Match match = MatchService.createRandomMatch();
        Match match2 = MatchService.createRandomMatch();
        for (int i = 0; i < 2; i++) {
            match.addPlayer(user);
            match2.addPlayer(user);
            match2.addPlayer(user);
        }

        var matches = List.of(match,match2);

        when(matchRepository.findByDeletedFalse()).thenReturn(matches);

        this.mvc.perform(get("/matches")
                        .cookie(new Cookie("token",jwtUtils.generateJwt(user)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(getMatchesResponse(matches)));
    }

    private String getMatchesResponse(List<Match> matches) {
        JsonObject allMatches = new JsonObject();
        JsonArray matchesArray = new JsonArray();
        matches.forEach(m -> matchesArray.add(JsonParser.parseString(m.toJsonString())));
        allMatches.add("matches", matchesArray);
        return allMatches.toString();
    }

    @Test
    void getMatch() throws Exception {
        User user = createUser();

        Match match = MatchService.createRandomMatch();
        when(matchRepository.findById(any())).thenReturn(Optional.of(match));

        this.mvc.perform(get("/matches/" + match.getId())
                        .cookie(new Cookie("token",jwtUtils.generateJwt(user)))
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(match.toJsonString()));
    }

    /**
     * Verifica: Obtencion de contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.
     */
    @Test
    void matchesCounterRetrieved() throws Exception {
        ///////							Test Empty Counter					///////
        ///////////////////////////////////////////////////////////////////////////
        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andExpect(content().json("{\"matches\": 0, \"players\": 0}"));


        ///////							Test Counter						///////
        ///////////////////////////////////////////////////////////////////////////

        // Valid matches: 1, Valid Players: 2
        Match m1 = MatchService.createRandomMatch();
        m1.addPlayer(createUser());
        m1.addPlayer(createUser());

        // Test
        when(matchRepository.findByCreatedAtAfter(any())).thenReturn(List.of(m1));
        when(playerRepository.findAll()).thenReturn(m1.getPlayers());

        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"matches\": 1, \"players\": 2}"));
    }

    @Test
    void registerNewPlayer() throws Exception {

        User user = createUser();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        Match match = MatchService.createRandomMatch();
        match.addPlayer(user);
        when(matchRepository.findById(any())).thenReturn(Optional.of(match));

        ResultActions request = this.mvc.perform(
                post("/matches/" + match.getId() + "/players")
                .cookie(new Cookie("token",jwtUtils.generateJwt(user)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        request.andExpect(status().isOk());

        assertFalse(matchRepository.findById(match.getId()).orElse(new Match()).getPlayers().isEmpty());
    }

    User createUser() {
        return new User("User", "User","0303456", "pp@g.com", "Password");
    }

	@Test
	void createMathOK() throws Exception {
        var user = createUser();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
		this.mvc.perform(post("/matches")
                .cookie(new Cookie("token",jwtUtils.generateJwt(user)))
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"location\": \"GRUN FC\",\n" +
				"   \"dateAndTime\": \"2023-09-04T17:00:00.000Z\"\n" +
				"}")).andExpect(status().isCreated());
	}
	@Test
	void createMatchBadRequest() throws Exception {
        var user = createUser();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
		this.mvc.perform(post("/matches")
                .cookie(new Cookie("token",jwtUtils.generateJwt(user)))
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"dateAndTime\": \"2023-09-04T17:00:00.000Z\",\n" +
                "   \"userId\": \"" + user.getId() + "\"\n" +
				"}")).andExpect(status().isBadRequest());
	}

}
