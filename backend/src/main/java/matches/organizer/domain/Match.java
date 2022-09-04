package matches.organizer.domain;

import matches.organizer.dto.MatchDTO;
import matches.organizer.exception.AddPlayerException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Match {

    private UUID id;
    private String name;
    private UUID userId;
    private LocalDate date;
    private LocalTime hour;
    private String location;
    private LocalDateTime createdAt;
    private List<Player> players;

    public Match(UUID id, String name, UUID userId, LocalDate date, LocalTime hour, String location, LocalDateTime createdAt){
       this.id = id;
       this.name = name;
       this.userId = userId;
       this.date = date;
       this.hour = hour;
       this.location = location;
       this.createdAt = createdAt;
       this.players = new ArrayList<>();
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

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public LocalDateTime getDateTime() { return hour.atDate(date);}

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = dateTime;
    }

    public List<Player> getPlayers() { return players; }

    public List<Player> getStartingPlayers() {
        return players.stream().limit(10).collect(Collectors.toList());
    }

    public List<Player> getSubstitutePlayers() {
        return players.stream().skip(10).limit(3).collect(Collectors.toList());
    }

    public void removeAllPlayers() {
        players = new ArrayList<>();
    }

    public void addPlayer(User user, String phone, String email) {
        if(players.size() >= 13)
            throw new AddPlayerException("Match: Cannot add player. The team is complete.");
        if(phone == null)
            throw new AddPlayerException("Match: Cannot add player. Phone cannot be null.");
        if(email == null)
            throw new AddPlayerException("Match: Cannot add player. Email cannot be null.");
        user.setPhone(phone);
        user.setEmail(email);
        players.add(new Player(user.getId()));
    }

    public MatchDTO getDto() {
        return new MatchDTO(this);
    }
}