package matches.organizer.service;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import matches.organizer.domain.User;
import matches.organizer.dto.CounterDTO;
import matches.organizer.storage.MatchRepository;
import matches.organizer.storage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    public MatchService(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public List<Match> getMatches() {
        return matchRepository.getAll();
    }

    public Match getMatch(UUID id) {
        return matchRepository.get(id);
    }

    public void updateMatch(Match match) {
        matchRepository.update(match);
    }

    public Match createMatch(Match newMatch){

        if (userRepository.get(newMatch.getUserId()) == null) {

            logger.error("USER NOT FOUND: NEED TO CREATE AND USER BEFORE");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }
        Match match = new MatchBuilder()
                .setName(newMatch.getName())
                .setUserId(newMatch.getUserId())
                .setDateAndTime(newMatch.getDateAndTime())
                .setLocation(newMatch.getLocation())
                .build();

        matchRepository.add(match);
        logger.info("NEW MATCH CREATED WITH ID: " + match.getId());
        return match;
  }


    public List<Player> registerNewPlayer(UUID id, User user) {
        var match = matchRepository.get(id);

        if(match != null) {
            addPlayerToMatch(match, user);
            matchRepository.update(match);
            logger.info("PLAYER WITH ID: " + user.getId().toString() + " ADDED CORRECTLY TO MATCH " + match.getId().toString());


            // TODO capaz estaría bueno loggear algo de esto

            /*
            System.out.println("Se agrega un player al match" + id
            + "\ncon el alias: " + user.getAlias()
            + "\ncon el telefono: " + phone
            + "\ncon el email " + email);
            */
            return match.getPlayers();
        } else {
            logger.error("MATCH NOT FOUND WITH ID: " + id.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found.");
        }
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
        logger.info(matches.size() + " MATCHES AND " + players.size() + "PLAYERS CONFIRMED IN THE LAST TWO HOURS ");

        return new CounterDTO(matches.size(), players.size());
    }

    public void addPlayerToMatch(Match match, User user) {
        if(user.getPhone() == null) {
            logger.error("CANNOT ADD PLAYER IF PHONE NUMBER IS NULL.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. Phone cannot be null.");
        }
        if(user.getEmail() == null){
            logger.error("CANNOT ADD PLAYER IF EMAIL IS NULL.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. Email cannot be null.");}
        match.addPlayer(user.getId());
        updateUser(user);
    }

    private void updateUser(User user) {
        if (userRepository.get(user.getId()) != null) {
            logger.error("USER DOES NOT EXISTS.");
            userRepository.update(user);
        } else {
            userRepository.add(user);
        }
    }
    
}
