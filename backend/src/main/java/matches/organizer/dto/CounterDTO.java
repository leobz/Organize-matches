package matches.organizer.dto;

public class CounterDTO {
    private final int matches;
    private final int players;

    public CounterDTO(int matches, int players) {
        this.matches = matches;
        this.players = players;
    }

    public int getPlayers() {
        return players;
    }

    public int getMatches() {
        return matches;
    }
}
