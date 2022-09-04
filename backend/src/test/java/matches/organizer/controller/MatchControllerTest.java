package matches.organizer.controller;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.service.MatchService;
import matches.organizer.storage.InMemoryMatchRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {MatchService.class, MatchController.class, InMemoryMatchRepository.class})
@AutoConfigureMockMvc
class MatchControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private MatchRepository matchRepository;


    @Test
    void matchesRetrieved() throws Exception {
        this.mvc.perform(get("/matches").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
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

        result = this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE));
        result = result.andExpect(status().isOk());
        result = result.andExpect(content().json("{'matches': 0, 'players': 0}"));


        ///////							Test Counter						///////
        ///////////////////////////////////////////////////////////////////////////

        // Valid matches: 1, Valid Players: 2
        Match m1 = createMatch();
        m1.addPlayer(createUser(), "", "");
        m1.addPlayer(createUser(), "", "");

        // Invalid matches and players (older than 2 hours)
        LocalDateTime oderDT = LocalDateTime.now().minusHours(2).minusMinutes(1);

        Match m2 = createMatch();
        m2.setCreatedAt(oderDT);

        m1.addPlayer(createUser(), "", "");
        m1.getPlayers().get(0).setConfirmedAt(oderDT);

        // Test
        matchRepository.add(m1);
        matchRepository.add(m2);

        result = this.mvc.perform(get("/matches/counter").accept(MediaType.APPLICATION_JSON_VALUE));
        result = result.andExpect(status().isOk());
        result = result.andExpect(content().json("{'matches': 1, 'players': 2}"));
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
}
