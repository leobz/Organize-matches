package matches.organizer.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.v3.oas.annotations.Operation;
import matches.organizer.domain.Match;
import matches.organizer.domain.Player;
import matches.organizer.dto.CounterDTO;
import matches.organizer.service.MatchService;
import matches.organizer.service.UserService;
import matches.organizer.util.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"https://partidos.com.ar", "https://www.partidos.com.ar", "http://localhost:3001"},
        allowedHeaders = {"https://partidos.com.ar", "https://www.partidos.com.ar", "http://localhost:3001"},
        allowCredentials = "true")
@RestController
@EnableWebMvc
public class MatchController {
    private final MatchService matchService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @Autowired
    public MatchController(MatchService matchService, UserService userService, JwtUtils jwtUtils) {
        this.matchService = matchService;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    Logger logger = LoggerFactory.getLogger(MatchController.class);

    @GetMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllMatches(@CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("GET TO: /matches ");
        jwtUtils.verify(auth);
        JsonObject allMatches = new JsonObject();
        JsonArray matchesArray = new JsonArray();
        matchService.getMatches().forEach(match -> matchesArray.add(JsonParser.parseString(match.toJsonString())));
        allMatches.add("matches", matchesArray);
        return allMatches.toString();
    }

    @PostMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.CREATED)
    public Match createMatch(@RequestBody Match newMatch, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("POST TO: /matches ");
        jwtUtils.verify(auth);

        newMatch.setUserId(jwtUtils.getUserFromToken(auth));
        return matchService.createMatch(newMatch);
    }

    @PatchMapping(value = "/matches/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    public Match editMatch(@PathVariable String matchId, @RequestBody Match newMatch, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("PATCH TO: /matches/{" + matchId + "}");
        jwtUtils.verify(auth);

        Match match = matchService.getMatch(matchId);
        String tokenUserId = jwtUtils.getUserFromToken(auth);
        logger.info("Match id " + matchId);
        logger.info("New Match id " + newMatch.getId().toString());
        logger.info("Token user id" + tokenUserId);
        logger.info("Match user id" + match.getUserId().toString());
        logger.info("Date time: " + match.getDateAndTime());
        logger.info("New Date time: " + newMatch.getDateAndTime());
        if(match.getUserId().compareTo(tokenUserId) != 0) {
            logger.error("CANNOT EDIT MATCH OWNED BY OTHER USERS");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot edit match owned by other users");
        }

        return matchService.editMatch(matchId, newMatch);
    }

    @GetMapping(value = "/matches/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMatch(@PathVariable String matchId, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("GET TO: /matches/{}", matchId);
        jwtUtils.verify(auth);
        return matchService.getMatch(matchId).toJsonString();
    }

    @DeleteMapping(value = "/matches/{matchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteMatch(@PathVariable String matchId, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{
        logger.info("DELETE TO: /matches/{}", matchId);
        jwtUtils.verify(auth);

        String userId = jwtUtils.getUserFromToken(auth);
        return matchService.removeMatch(matchId, userId).toJsonString();
    }

    @Operation(summary = "Retorna un contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.")
    @GetMapping(value = "/matches/counter", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CounterDTO getMatchAndPlayerCounter() {
        logger.info("GET TO: /matches/counter ");
        LocalDateTime from = LocalDateTime.now(ZoneOffset.UTC).minusHours(2);
        return matchService.getMatchAndPlayerCounterFrom(from);
    }

    @PostMapping(value = "/matches/{matchId}/players", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Player>> registerPlayer(@PathVariable String matchId, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{

        logger.info("POST TO: /matches/{}/players ", matchId);
        jwtUtils.verify(auth);

        String userId = jwtUtils.getUserFromToken(auth);

        var user = userService.getUser(userId);
        if (user == null) {
            logger.error("USER NOT FOUND: NEED TO CREATE AND USER BEFORE");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        matchService.registerNewPlayer(matchId, user);
        Match match = matchService.getMatch(matchId);
        Map<String, List<Player>> response = new HashMap<>();
        response.put("startingPlayers", match.getStartingPlayers());
        response.put("substitutePlayers", match.getSubstitutePlayers());

        return response;
    }

    @DeleteMapping(value = "/matches/{matchId}/players/{playerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Player>> unregisterPlayer(@PathVariable String matchId, @PathVariable String playerId, @CookieValue(value = "token", defaultValue = "") String auth) throws Exception{

        logger.info("DELETE TO: /matches/{"+ matchId+"}/players/{"+playerId+"}");
        jwtUtils.verify(auth);

        String userId = jwtUtils.getUserFromToken(auth);
        logger.info("player id: "+playerId);
        logger.info("user id: "+userId);

        if(userId.compareTo(playerId) != 0) {
            logger.error("CANNOT UNSUBSCRIBE ANOTHER USER");
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot unsubscribe another user");
        }

        var user = userService.getUser(userId);
        if (user == null) {
            logger.error("USER NOT FOUND: NEED TO CREATE AND USER BEFORE");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        matchService.unregisterPlayer(matchId, playerId);
        var match = matchService.getMatch(matchId);
        Map<String, List<Player>> response = new HashMap<String, List<Player>>();
        response.put("startingPlayers", match.getStartingPlayers());
        response.put("substitutePlayers", match.getSubstitutePlayers());

        return response;
    }
}