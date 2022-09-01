package matches.organizer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MatchBuilder {

    private String name;
    private UUID user_id;
    private LocalDate date;
    private LocalTime hour;
    private String location;

    public MatchBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MatchBuilder setUserId(UUID user_id) {
        this.user_id = user_id;
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
        if(user_id == null)
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
        LocalDateTime creationDate = LocalDateTime.now();
        List<Player> players = new ArrayList<Player>();

        return new Match(id, name, user_id, date, hour, location, creationDate, players);
    }
}