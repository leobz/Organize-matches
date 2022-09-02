package matches.organizer.storage;

import matches.organizer.domain.Match;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class InMemoryMatchRepository implements MatchRepository {

    private final List<Match> matches = new ArrayList<>();

    @Override
    public Match get(UUID id) {
        return matches.stream()
                .filter(match -> hasSameId(id, match))
                .findFirst().orElse(null);
    }

    @Override
    public List<Match> getAll() {
        return matches;
    }

    @Override
    public void add(Match match) {
        matches.add(match);
    }

    @Override
    public void update(Match match) {
        if (matches.removeIf(anyMatch -> hasSameId(anyMatch.getId(), match))) {
            add(match);
        }
    }

    @Override
    public void remove(Match match) {
        matches.removeIf(anyMatch -> hasSameId(anyMatch.getId(), match));
    }

    private static boolean hasSameId(UUID anyMatchId, Match match) {
        return anyMatchId.toString().equals(match.getId().toString());
    }
}
