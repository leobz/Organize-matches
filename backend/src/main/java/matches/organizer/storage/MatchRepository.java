package matches.organizer.storage;

import matches.organizer.domain.Match;

import java.util.List;

public interface MatchRepository {
    Match get(String id);
    List<Match> getAll();
    void add(Match match);
    void update(Match match);
    void remove(Match match);
}
