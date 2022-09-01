package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import matches.organizer.storage.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    public List<Match> getMatches() {
        Player anyPlayer = new Player("Arthur Friedenreich");
        Match anyMatch = new MatchBuilder()
                .setName("Nuestro partido")
                .setUserId(UUID.randomUUID())
                .setDate(LocalDate.now().plusDays(1))
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anyMatch.addPlayer(anyPlayer);
        matchRepository.add(anyMatch);
        return matchRepository.getAll();
    }
}
