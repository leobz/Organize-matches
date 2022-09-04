package matches.organizer.dto;

import matches.organizer.domain.Match;
import matches.organizer.domain.Player;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MatchDTO {

    private UUID id;
    private String name;
    private UUID userId;
    private LocalDate date;
    private LocalTime hour;
    private String location;
    private LocalDateTime createdAt;
    private List<PlayerDTO> startingPlayers;
    private List<PlayerDTO> substitutePlayers;

    public MatchDTO(Match match){
        this.id = match.getId();
        this.name = match.getName();
        this.userId = match.getUserId();
        this.date = match.getDate();
        this.hour = match.getHour();
        this.location = match.getLocation();
        this.createdAt = match.getCreatedAt();
        this.startingPlayers = match.getStartingPlayers().stream().map(Player::getDto).collect(Collectors.toList());
        this.substitutePlayers = match.getSubstitutePlayers().stream().map(Player::getDto).collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getDate() {
        return date.toString();
    }

    public String getHour() {
        return hour.toString();
    }

    public String getLocation() {
        return location;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }

    public List<PlayerDTO> getStartingPlayers() {
        return startingPlayers;
    }

    public List<PlayerDTO> getSubstitutePlayers() { return substitutePlayers; }
}
