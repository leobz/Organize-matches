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
    private UUID user_id;
    private LocalDate date;
    private LocalTime hour;
    private String location;
    private LocalDateTime creationDate;
    private List<PlayerDTO> startingPlayers;
    private List<PlayerDTO> substitutePlayers;

    public MatchDTO(Match match){
        this.id = match.getId();
        this.name = match.getName();
        this.user_id = match.getUserId();
        this.date = match.getDate();
        this.hour = match.getHour();
        this.location = match.getLocation();
        this.creationDate = match.getCreationDate();
        this.startingPlayers = match.getStartingPlayers().stream().map(p -> p.getDto()).collect(Collectors.toList());
        this.substitutePlayers = match.getSubstitutePlayers().stream().map(p -> p.getDto()).collect(Collectors.toList());
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

    public List<PlayerDTO> getStartingPlayers() {
        return startingPlayers;
    }

    public List<PlayerDTO> getSubstitutePlayers() { return substitutePlayers; }
}