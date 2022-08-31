package matches.organizer.storage;

import matches.organizer.domain.Match;

import java.util.List;
import java.util.UUID;

public interface MatchRepository {
    Match get(UUID id);
    List<Match> getAll();
    void add(Match match);
    void update(Match match);
    void remove(Match match);
}
