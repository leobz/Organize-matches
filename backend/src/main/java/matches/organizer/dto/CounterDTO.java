package matches.organizer.dto;

public class CounterDTO {
    private final Long matches;
    private final Long players;

    public CounterDTO(Long matches, Long players) {
        this.matches = matches;
        this.players = players;
    }

    public Long getPlayers() {
        return players;
    }

    public Long getMatches() {
        return matches;
    }
}
