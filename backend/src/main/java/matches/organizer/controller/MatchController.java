package matches.organizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import matches.organizer.domain.Player;
import matches.organizer.dto.CounterDTO;
import matches.organizer.dto.MatchDTO;
import matches.organizer.dto.POSTMatchDTO;
import matches.organizer.dto.RegisterPlayerDTO;
import matches.organizer.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@EnableWebMvc
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<MatchDTO> getAllMatches() {
        return matchService.getMatches().stream().map(match -> match.getDto()).collect(Collectors.toList());
    }


    @PostMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE )
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Object createMatch(@RequestBody POSTMatchDTO newMatch){

        matchService.createMatch(newMatch);
        return newMatch;
    }



    @Operation(summary = "Retorna un contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.")
    @GetMapping(value = "/matches/counter", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CounterDTO getMatchAndPlayerCounter() {
        LocalDateTime from = LocalDateTime.now().minusHours(2);
        return matchService.getMatchAndPlayerCounterFrom(from);
    }

    @PostMapping(value = "/matches/{matchId}/players", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Player> registerPlayer(@PathVariable UUID matchId, @RequestBody RegisterPlayerDTO registerPlayerDTO) {
        return matchService.registerNewPlayer(matchId, registerPlayerDTO.user, registerPlayerDTO.phone, registerPlayerDTO.email) ;
    }

}