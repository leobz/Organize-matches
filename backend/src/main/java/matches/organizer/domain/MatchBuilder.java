package matches.organizer.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: name is missing");
        if(userId == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: user id is missing");
        if(date == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date is missing");
        if(hour == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: hour is missing");
        if(location == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: location is missing");
        if(!LocalDateTime.now(ZoneOffset.UTC).isBefore(LocalDateTime.of(date, hour)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date and hour is in the past");
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
        return new Match(id, name, userId, date, hour, location, createdAt);
    }
}