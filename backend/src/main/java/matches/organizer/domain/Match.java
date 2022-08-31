package matches.organizer.domain;

import matches.organizer.dto.MatchDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Match {

    private UUID id;
    private String name;
    private UUID user_id;
    private LocalDate date;
    private LocalTime hour;
    private String location;
    private LocalDateTime creationDate;
    private List<Player> players;

    public Match(UUID id, String name, UUID user_id, LocalDate date, LocalTime hour, String location, LocalDateTime creationDate, List<Player> players){
       this.id = id;
       this.name = name;
       this.user_id = user_id;
       this.date = date;
       this.hour = hour;
       this.location = location;
       this.creationDate = creationDate;
       this.players = players;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getUserId() {
        return user_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<Player> getPlayers() { return players; }

    public List<Player> getStartingPlayers() {
        return players.stream().limit(10).collect(Collectors.toList());
    }

    public List<Player> getSubstitutePlayers() {
        return players.stream().skip(10).limit(3).collect(Collectors.toList());
    }

    public void addPlayer(Player player) throws Exception {
        if(players.size() >= 13)
            throw new Exception("Match: Cannot add player. The team is complete.");
        players.add(player);
    }

    public MatchDTO getDto() {
        return new MatchDTO(this);
    }
}