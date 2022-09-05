package matches.organizer.controller;

import matches.organizer.domain.Match;
import matches.organizer.dto.MatchDTO;
import matches.organizer.dto.POSTMatchDTO;
import matches.organizer.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
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

    @PostMapping(value = "/matches", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createMatch(@RequestBody POSTMatchDTO oneMatch){
        return "Partido creado con ID: " + matchService.createMatchFromPost(oneMatch.getName(),oneMatch.getLocation(),oneMatch.getDate(),oneMatch.getHour());
    }





}