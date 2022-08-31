package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {
    public List<Match> getMatches() {
        try {
            Player anyPlayer = new Player("Arthur Friedenreich");
            Match anyMatch = new MatchBuilder()
                    .setName("Nuestro partido")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now())
                    .setHour(LocalTime.now())
                    .setLocation("La Bombonera")
                    .build();
            anyMatch.addPlayer(anyPlayer);
            return List.of(anyMatch);
        }
        catch (Exception e) {
            return null;
        }
    }
}
