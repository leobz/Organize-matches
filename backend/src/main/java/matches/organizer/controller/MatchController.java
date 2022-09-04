package matches.organizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import matches.organizer.dto.CounterDTO;
import matches.organizer.dto.MatchDTO;
import matches.organizer.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;
import java.util.List;
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

    @Operation(summary = "Retorna un contador con la cantidad de partidos creados y jugadores anotados en las últimas 2 horas.")
    @GetMapping(value = "/matches/counter", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CounterDTO getMatchAndPlayerCounter() {
        LocalDateTime from = LocalDateTime.now().minusHours(2);
        return matchService.getMatchAndPlayerCounterFrom(from);
    }
}