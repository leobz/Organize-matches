package matches.organizer.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import matches.organizer.domain.Match;
import matches.organizer.dto.MatchDTO;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.dto.RegisterPlayerDTO;
import matches.organizer.service.MatchService;
import matches.organizer.storage.InMemoryMatchRepository;
import matches.organizer.storage.InMemoryUserRepository;
import matches.organizer.storage.MatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.time.temporal.Temporal;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {MatchService.class, MatchController.class, InMemoryMatchRepository.class, InMemoryUserRepository.class})
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MatchRepository matchRepository;

    @Test
    void matchesRetrieved() throws Exception {
        sanitize();

        Match match = createMatch();
        Match match2 = createMatch();
        for (int i = 0; i < 2; i++) {
            match.addPlayer(UUID.randomUUID());
            match2.addPlayer(UUID.randomUUID());
            match2.addPlayer(UUID.randomUUID());
        }

        matchRepository.add(match);
        matchRepository.add(match2);

        ArrayList<MatchDTO> matches = new ArrayList<MatchDTO>();

        matches.add(match.getDto());
        matches.add(match2.getDto());

        Gson gson = getJsonParser();

        this.mvc.perform(get("/matches")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(matches)));
    }

    @Test
    void getMatch() throws Exception {
        sanitize();

        Match match = createMatch();
        matchRepository.add(match);

        Gson gson = getJsonParser();

        this.mvc.perform(get("/matches/" + match.getId())
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().json(gson.toJson(match.getDto())));
    }

    /**
     * Verifica: Obtencion de contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.
     */
    @Test
    void matchesCounterRetrieved() throws Exception {
        ResultActions result;

        ///////							Test Empty Counter					///////
        ///////////////////////////////////////////////////////////////////////////
        sanitize();

        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk()).andExpect(content().json("{\"matches\": 0, \"players\": 0}"));


        ///////							Test Counter						///////
        ///////////////////////////////////////////////////////////////////////////

        // Valid matches: 1, Valid Players: 2
        Match m1 = createMatch();
        m1.addPlayer(UUID.randomUUID());
        m1.addPlayer(UUID.randomUUID());

        // Invalid matches and players (older than 2 hours)
        LocalDateTime oderDT = LocalDateTime.now().minusHours(2).minusMinutes(1);

        Match m2 = createMatch();
        m2.setCreatedAt(oderDT);

        m1.addPlayer(UUID.randomUUID());
        m1.getPlayers().get(0).setConfirmedAt(oderDT);

        // Test
        matchRepository.add(m1);
        matchRepository.add(m2);

        this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"matches\": 1, \"players\": 2}"));
    }

    @Test
    public void registerNewPlayer() throws Exception {

        Match match = createMatch();
        matchRepository.add(match);

        RegisterPlayerDTO registerPlayerDTO = new RegisterPlayerDTO();
        registerPlayerDTO.user = new User("alias");
        registerPlayerDTO.email = "email";
        registerPlayerDTO.phone = "phone";

        assertTrue(matchRepository.get(match.getId()).getPlayers().isEmpty());

        this.mvc.perform(
                post( "/matches/" + match.getId() + "/players")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(
                                "  {\n" +
                                        "    \"user\" : {\n" +
                                        "    \"alias\" : \"y2\"\n" +
                                        "    },\n" +
                                        "    \"phone\" : \"54241248\",\n" +
                                        "    \"email\" : \"helpme@gmail.com\"\n" +
                                        "}")
                        .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());

        assertFalse(matchRepository.get(match.getId()).getPlayers().isEmpty());

    }

    void sanitize() {
        matchRepository.getAll().clear();
    }

    Match createMatch() {
        return new MatchBuilder().
                setName("Match").
                setLocation("Location").
                setUserId(UUID.randomUUID()).
                setDate(LocalDate.now().plusDays(1))
                .setHour(LocalTime.now())
                .build();
    }

    User createUser() {
        return new User("User", "User", "Password");
    }

    Gson getJsonParser() {
        JsonSerializer<Temporal> localDateTimeSerializer = (src, type, context) -> new JsonPrimitive(src.toString());

        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer)
                .registerTypeAdapter(LocalDate.class, localDateTimeSerializer)
                .registerTypeAdapter(LocalTime.class, localDateTimeSerializer)
                .create();
    }

	@Test
	void createMathOK() throws Exception {

		this.mvc.perform(post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"location\": \"GRUN FC\",\n" +
				"   \"date\": \"2023-09-04\",\n" +
				"   \"hour\": \"17:00:00\",\n" +
				"   \"userId\": \"fbc82470-1c30-4ad6-bff4-a2181dddf747\"\n" +
				"}")).andExpect(status().isCreated());

	}
	@Test
	void createMatchBadRequest() throws Exception {
	//TODO Configurar como BAD_REQUEST
		this.mvc.perform(post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"date\": \"2023-09-04\",\n" +
				"   \"hour\": \"17:00:00\"\n" +
				"}")).andExpect(status().isInternalServerError());



	}

}
