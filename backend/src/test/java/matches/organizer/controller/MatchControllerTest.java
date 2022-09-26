package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.service.MatchService;
import matches.organizer.service.UserService;
import matches.organizer.storage.InMemoryMatchRepository;
import matches.organizer.storage.InMemoryUserRepository;
import matches.organizer.storage.MatchRepository;
import matches.organizer.storage.UserRepository;
import matches.organizer.util.JwtUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {JwtUtils.class, MatchService.class, UserService.class, MatchController.class, InMemoryMatchRepository.class, InMemoryUserRepository.class})
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void matchesRetrieved() throws Exception {
        sanitize();

        User user = createUser();
        Match match = MatchService.createRandomMatch();
        Match match2 = MatchService.createRandomMatch();
        for (int i = 0; i < 2; i++) {
            match.addPlayer(user);
            match2.addPlayer(user);
            match2.addPlayer(user);
        }

        matchRepository.add(match);
        matchRepository.add(match2);

        ArrayList<Match> matches = new ArrayList<>();

        matches.add(match);
        matches.add(match2);

        this.mvc.perform(get("/matches")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(getMatchesResponse(matches)));
    }

    private String getMatchesResponse(ArrayList<Match> matches) {
        JsonObject allMatches = new JsonObject();
        JsonArray matchesArray = new JsonArray();
        matches.forEach(m -> matchesArray.add(JsonParser.parseString(m.toJsonString())));
        allMatches.add("matches", matchesArray);
        return allMatches.toString();
    }

    @Test
    void getMatch() throws Exception {
        sanitize();

        Match match = MatchService.createRandomMatch();
        matchRepository.add(match);

        this.mvc.perform(get("/matches/" + match.getId())
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
        sanitize();
        User user = createUser();

        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andExpect(content().json("{\"matches\": 0, \"players\": 0}"));


        ///////							Test Counter						///////
        ///////////////////////////////////////////////////////////////////////////

        // Valid matches: 1, Valid Players: 2
        Match m1 = MatchService.createRandomMatch();
        m1.addPlayer(user);
        m1.addPlayer(user);

        // Invalid matches and players (older than 2 hours)
        LocalDateTime oderDT = LocalDateTime.now(ZoneOffset.UTC).minusHours(2).minusMinutes(1);

        Match m2 = MatchService.createRandomMatch();
        m2.setCreatedAt(oderDT);

        m1.addPlayer(user);
        m1.getPlayers().get(0).setConfirmedAt(oderDT);

        // Test
        matchRepository.add(m1);
        matchRepository.add(m2);

        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"matches\": 1, \"players\": 2}"));
    }

    @Test
    void registerNewPlayer() throws Exception {
        sanitize();

        User user = createUser();
        userRepository.add(user);
        UUID userID = user.getId();

        Match match = MatchService.createRandomMatch();
        matchRepository.add(match);

        assertTrue(matchRepository.get(match.getId()).getPlayers().isEmpty());

        ResultActions request = this.mvc.perform(
                post("/matches/" + match.getId() + "/players")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("\""+ userID + "\"")
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        request.andExpect(status().isOk());

        assertFalse(matchRepository.get(match.getId()).getPlayers().isEmpty());

    }

    void sanitize() {
        matchRepository.getAll().clear();
        userRepository.getAll().clear();
    }

    User createUser() {
        return new User("User", "User","0303456", "pp@g.com", "Password");
    }

	@Test
	void createMathOK() throws Exception {
        var user = createUser();
        userRepository.add(user);
		this.mvc.perform(post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"location\": \"GRUN FC\",\n" +
				"   \"dateAndTime\": \"2023-09-04T17:00:00.000Z\",\n" +
				"   \"userId\": \"" + user.getId() + "\"\n" +
				"}")).andExpect(status().isCreated());

	}
	@Test
	void createMatchBadRequest() throws Exception {
        var user = createUser();
        userRepository.add(user);
		this.mvc.perform(post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"dateAndTime\": \"2023-09-04T17:00:00.000Z\",\n" +
                "   \"userId\": \"" + user.getId() + "\"\n" +
				"}")).andExpect(status().isBadRequest());



	}

}
