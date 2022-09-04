package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import matches.organizer.domain.User;
import matches.organizer.dto.CounterDTO;
import matches.organizer.exception.AddPlayerException;
import matches.organizer.storage.MatchRepository;
import matches.organizer.storage.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
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
        addPlayerToMatch(anyMatch, anyUser, "1234-5678", "afriedenreich@gmail.com");
        matchRepository.add(anyMatch);
        return matchRepository.getAll();
    }

    /**
     * Retorna un contador con la cantidad de partidos creados y jugadores anotados a partir de una fecha/hora.
     *
     * @param from DateTime a partir del cual se buscaran los partidos y jugadores
     * @see CounterDTO
     */
    public CounterDTO getMatchAndPlayerCounterFrom(LocalDateTime from) {
        List<Match> matches = matchRepository.getAllCreatedFrom(from);
        List<Player> players = matchRepository.getAllPlayersConfirmedFrom(from);

        return new CounterDTO(matches.size(), players.size());
    }

    public void addPlayerToMatch(Match match, User user, String phone, String email) {
        if(match.getPlayers().size() >= 13)
            throw new AddPlayerException("Match: Cannot add player. The team is complete.");
        if(phone == null)
            throw new AddPlayerException("Match: Cannot add player. Phone cannot be null.");
        if(email == null)
            throw new AddPlayerException("Match: Cannot add player. Email cannot be null.");
        user.setPhone(phone);
        user.setEmail(email);
        if (userRepository.get(user.getId()) != null) {
            userRepository.update(user);
        } else {
            userRepository.add(user);
        }
        match.addPlayer(user.getId());
    }
}
