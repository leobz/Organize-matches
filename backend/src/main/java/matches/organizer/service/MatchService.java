package matches.organizer.service;

import matches.organizer.domain.*;
import matches.organizer.dto.CounterDTO;
import matches.organizer.storage.MatchRepository;
import matches.organizer.storage.StatisticRepository;
import matches.organizer.storage.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class MatchService {


    MongoTemplate mongoTemplate;
    MatchRepository matchRepository;
    UserRepository userRepository;
    StatisticRepository statisticRepository;


    Logger logger = LoggerFactory.getLogger(MatchService.class);

    @Autowired
    public MatchService(MatchRepository matchRepository, UserRepository userRepository, StatisticRepository statisticRepository, MongoTemplate mongoTemplate) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
        this.statisticRepository = statisticRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Match> getMatches() {
        return matchRepository.findByDeletedFalse();
    }

    public Match getMatch(String id) {
        return matchRepository.findById(id).orElse(null);
    }

    public void updateMatch(Match match) {
        matchRepository.save(match);
    }

    public Match removeMatch(String matchId, String userId) {
        Match match = matchRepository.findById(matchId).get();
        if (match.getUserId().equals(userId)){
            match.setDeleted(true);
            matchRepository.save(match);
            logger.info("MATCH DELETED WITH ID: {}", match.getId());
            return match;
        } else {
            logger.error("NO MATCH DELETED. USER IS NOT THE MATCH OWNER");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot delete match. User is not the match owner.");
        }
    }

    public Match createMatch(Match newMatch){

        if (userRepository.findById(newMatch.getUserId()).orElse(null) == null) {

            logger.error("USER NOT FOUND: NEED TO CREATE AND USER BEFORE");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");

        }
        Match match = new MatchBuilder()
                .setName(newMatch.getName())
                .setUserId(newMatch.getUserId())
                .setDateAndTime(newMatch.getDateAndTime())
                .setLocation(newMatch.getLocation())
                .build();

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Match>> violations = validator.validate(match);

        if (violations.isEmpty()) {
            matchRepository.save(match);
            logger.info("NEW MATCH CREATED WITH ID: {}", match.getId());
            Statistic statistic = new Statistic(1);
            statisticRepository.save(statistic);


            return match;
        } else {
            for (ConstraintViolation<Match> violation : violations) {
                logger.info(violation.getMessage());
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Campos inválidos");
        }
    }

    public Match editMatch(String matchId, Match newMatch) {
        Match oldMatch = matchRepository.findById(matchId).orElse(null);
        if (oldMatch == null) {
            logger.error("MATCH NOT FOUND: CANNOT EDIT MATCH");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not foundL cannot edit match");
        }

        Match editedMatch = new MatchBuilder().fromMatch(oldMatch)
                .setName(newMatch.getName())
                .setDateAndTime(newMatch.getDateAndTime())
                .setLocation(newMatch.getLocation())
                .build();

        matchRepository.save(editedMatch);
        logger.info("UPDATED MATCH WITH ID: " + editedMatch.getId());
        return editedMatch;
    }


    public static Match createRandomMatch() {
        return new MatchBuilder().
                setName("Match").
                setLocation("Location").
                setUserId(UUID.randomUUID().toString()).
                setDateAndTime(LocalDateTime.now(ZoneOffset.UTC).plusDays(1))
                .build();
    }

    public void createAndSaveRandomMatch() {
        Match match = createRandomMatch();
        matchRepository.save(match);
    }

    public void registerNewPlayer(String id, User user) {
        Match match = matchRepository.findById(id).orElse(null);

        if (match != null) {
            logger.info("LEST TRY WITH: {} , {}", user.getId(), match.getId());
            addPlayerToMatch(match, user);
            updateMatchPlayers(match);

            Statistic statistic = new Statistic(2);
            statisticRepository.save(statistic);

            logger.info("PLAYER WITH ID: {} ADDED CORRECTLY TO MATCH: {}", user.getId(), match.getId());

        } else {
            logger.error("MATCH NOT FOUND WITH ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found.");
        }
    }

    private void updateMatchPlayers(Match match) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(match.getId())
                .andOperator(Criteria.where("$where").is("this.players.length < 13")));
        Update update = new Update();
        update.set("players", match.getPlayers());
        if (mongoTemplate.findAndModify(query, update, Match.class)==null) {
            logger.error("MATCH {} BECOMES FULL!!", match.getId());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Match becomes full!");
        }
    }

    public List<Player> unregisterPlayer(String matchId, String playerId) {
        Match match = matchRepository.findById(matchId).orElse(null);

        if (match != null) {
            try {
                match.removePlayer(playerId);
            } catch (Match.RemovePlayerException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot remove player. The user is not in the team.");
            }
            matchRepository.save(match);
            logger.info("PLAYER WITH ID: " + playerId + " REMOVED CORRECTLY FROM MATCH " + match.getId());
            return match.getPlayers();
        } else {
            logger.error("MATCH NOT FOUND WITH ID: " + matchId);
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
        Long matches = statisticRepository.countStatisticByStatisticType(1);
        Long player = statisticRepository.countStatisticByStatisticType(2);
        logger.info("{} MATCHES AND {} PLAYERS CONFIRMED IN THE LAST TWO HOURS ", matches, player);
        return new CounterDTO(matches, player);
    }

    public void addPlayerToMatch(Match match, User user) {
        if (user.getPhone() == null) {
            logger.error("CANNOT ADD PLAYER IF PHONE NUMBER IS NULL.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. Phone cannot be null.");
        }
        if (user.getEmail() == null) {
            logger.error("CANNOT ADD PLAYER IF EMAIL IS NULL.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. Email cannot be null.");
        }
        try {
            match.addPlayer(user);
        } catch(Match.AddPlayerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. The team is complete.");
        }
        updateUser(user);
    }


    private void updateUser(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            logger.error("USER DOES NOT EXISTS.");
        }
        userRepository.save(user);
    }
}