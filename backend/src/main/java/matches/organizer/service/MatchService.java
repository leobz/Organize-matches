package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import matches.organizer.storage.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }
    public List<Match> getMatches() {
        User anyUser = new User("Arthur", "Arthur Friedenreich", "Mi contraseña secreta");
        Match anyMatch = new MatchBuilder()
                .setName("Nuestro partido")
                .setUserId(UUID.randomUUID())
                .setDate(LocalDate.now().plusDays(1))
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anyMatch.addPlayer(anyUser, "1234-5678", "afriedenreich@gmail.com");
        matchRepository.add(anyMatch);
        return matchRepository.getAll();
    }


    public String createMatchFromPost(String matchName, String matchLocation, LocalDate matchDate, LocalTime matchHour){



        Match anyMatch = new MatchBuilder()
                .setName(matchName)
                .setUserId(UUID.randomUUID())
                .setDate(matchDate)
                .setHour(matchHour)
                .setLocation(matchLocation)
                .build();

        matchRepository.add(anyMatch);


        return anyMatch.getId().toString();
    }
}
