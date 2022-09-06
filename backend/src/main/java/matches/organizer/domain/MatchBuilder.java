package matches.organizer.domain;

import matches.organizer.exception.MatchBuildException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class MatchBuilder {

    private String name;
    private UUID userId;
    private LocalDate date;
    private LocalTime hour;
    private String location;

    public MatchBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MatchBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public MatchBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public MatchBuilder setHour(LocalTime hour) {
        this.hour = hour;
        return this;
    }

    public MatchBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public Match build() {
        if(name == null)
            throw new MatchBuildException("MatchBuilder: name is missing");
        if(userId == null)
            throw new MatchBuildException("MatchBuilder: user id is missing");
        if(date == null)
            throw new MatchBuildException("MatchBuilder: date is missing");
        if(hour == null)
            throw new MatchBuildException("MatchBuilder: hour is missing");
        if(location == null)
            throw new MatchBuildException("MatchBuilder: location is missing");
        if(!LocalDateTime.now().isBefore(LocalDateTime.of(date, hour)))
            throw new MatchBuildException("MatchBuilder: date and hour is in the past");
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        return new Match(id, name, userId, date, hour, location, createdAt);
    }
}