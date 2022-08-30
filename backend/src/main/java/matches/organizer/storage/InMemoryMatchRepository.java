package matches.organizer.storage;

import matches.organizer.domain.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryMatchRepository implements MatchRepository {

    private final List<Match> matches = new ArrayList<>();

    @Override
    public Match get(String id) {
        return matches.stream()
                .filter(match -> id.equals(match.getId()))
                .findFirst().orElse(null);
    }

    @Override
    public void add(Match match) {
        matches.add(match);
    }

    @Override
    public void update(Match match) {

    }

    @Override
    public void remove(Match match) {

    }
}
