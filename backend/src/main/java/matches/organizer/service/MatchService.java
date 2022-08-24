package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    public List<Match> getMatches() {
        var anyPlayer = new Player("Arthur Friedenreich");
        var anyMatch = new Match();
        anyMatch.getPlayers().add(anyPlayer);
        return List.of(anyMatch);
    }
}
