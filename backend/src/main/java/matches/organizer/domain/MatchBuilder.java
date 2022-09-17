package matches.organizer.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class MatchBuilder {

    private String name;
    private UUID userId;
    private LocalDateTime dateAndTime;
    private String location;

    public MatchBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MatchBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public MatchBuilder setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
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
        if(dateAndTime == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date and time is missing");
        if(location == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: location is missing");
        if(!LocalDateTime.now(ZoneOffset.UTC).isBefore(dateAndTime))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date and hour is in the past");
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
        return new Match(id, name, userId, dateAndTime, location, createdAt);
    }
}