package matches.organizer.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class MatchBuilder {

    private String id = UUID.randomUUID().toString();
    private String name;
    private String userId;
    private LocalDateTime dateAndTime;
    private String location;

    Logger logger = LoggerFactory.getLogger(MatchBuilder.class);

    public MatchBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MatchBuilder setUserId(String userId) {
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

    public MatchBuilder fromMatch(Match match) {
        id = match.getId();
        userId = match.getUserId();
        name = match.getName();
        dateAndTime = match.getDateAndTime();
        location = match.getLocation();
        return this;
    }

    public Match build() {
        if(name == null){
            logger.error("CANNOT CREATE A MATCH WITHOUT NAME");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: name is missing");
        }
        if(userId == null) {
            logger.error("CANNOT CREATE A MATCH WITHOUT USER ID");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: user id is missing");
        }
        if(dateAndTime == null){
            logger.error("CANNOT CREATE A MATCH WITHOUT DATE AND TIME");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date and time is missing");
        }
        if(location == null) {
            logger.error("CANNOT CREATE A MATCH WITHOUT LOCATION");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: location is missing");
        }
        if(!LocalDateTime.now(ZoneOffset.UTC).isBefore(dateAndTime)) {
            logger.error("CANNOT CREATE A MATCH IN THE PAST");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "MatchBuilder: date and hour is in the past");
        }
        LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
        return new Match(id, name, userId, dateAndTime, location, createdAt);
    }
}