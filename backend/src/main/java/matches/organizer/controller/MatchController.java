package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.v3.oas.annotations.Operation;
import matches.organizer.domain.Match;
import matches.organizer.domain.User;
import matches.organizer.dto.CounterDTO;
import matches.organizer.service.MatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@EnableWebMvc
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    Logger logger = LoggerFactory.getLogger(MatchController.class);


    @GetMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllMatches(@CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("GET TO: /matches ");
        matchService.jwtUtils.verify(auth);
        JsonObject allMatches = new JsonObject();
        JsonArray matchesArray = new JsonArray();
        matchService.getMatches().forEach(match -> matchesArray.add(JsonParser.parseString(match.toJsonString())));
        allMatches.add("matches", matchesArray);
        return allMatches.toString();
    }

    @PostMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.CREATED)
    public String createMatch(@RequestBody Match newMatch, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("POST TO: /matches ");
        matchService.jwtUtils.verify(auth);
        return matchService.createMatch(newMatch).toJsonString();
    }

    @GetMapping(value = "/matches/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMatch(@PathVariable UUID matchId, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("GET TO: /matches/{" + matchId.toString() + "}");
        matchService.jwtUtils.verify(auth);
        return matchService.getMatch(matchId).toJsonString();
    }

    @Operation(summary = "Retorna un contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.")
    @GetMapping(value = "/matches/counter", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CounterDTO getMatchAndPlayerCounter() {
        logger.info("GET TO: /matches/counter ");
        LocalDateTime from = LocalDateTime.now(ZoneOffset.UTC).minusHours(2);
        return matchService.getMatchAndPlayerCounterFrom(from);
    }

    @PostMapping(value = "/matches/{matchId}/players", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registerPlayer(@PathVariable UUID matchId, @RequestBody User user, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("POST TO: /matches/{"+ user.getId().toString() +"}/players ");
        matchService.jwtUtils.verify(auth);
        return Match.getPlayersJsonArray(matchService.registerNewPlayer(matchId, user)).toString();
    }

}