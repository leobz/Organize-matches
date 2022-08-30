package matches.organizer.storage;

import matches.organizer.domain.Match;

public interface MatchRepository {
    Match get(String id);
    void add(Match match);
    void update(Match match);
    void remove(Match match);
}
