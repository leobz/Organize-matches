package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.Player;
import matches.organizer.storage.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    public List<Match> getMatches() {
        var anyPlayer = new Player("Arthur Friedenreich");
        var anyMatch = new Match();
        anyMatch.getPlayers().add(anyPlayer);
        matchRepository.add(anyMatch);
        return matchRepository.getAll();
    }
}
