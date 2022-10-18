package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestInstance(PER_CLASS)
public class MatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;
    private Match anyMatch;
    private Match anotherMatch;

    @BeforeEach
    void setUp() {
        anyMatch = buildAMatch();
        anotherMatch = buildAnotherMatch();
    }

    @Test
    void addAndGetMatch() {
        matchRepository.save(anyMatch);
        matchRepository.save(anotherMatch);
        Assertions.assertEquals(anyMatch.getId(),matchRepository.findById(anyMatch.getId()).orElse(new Match()).getId());
        Assertions.assertEquals(anotherMatch.getId(),matchRepository.findById(anotherMatch.getId()).orElse(new Match()).getId());
    }

    @Test
    void addAndRemoveMatch() {
        matchRepository.save(anyMatch);
        matchRepository.save(anotherMatch);
        anyMatch.setDeleted(true);
        matchRepository.save(anyMatch);
        Assertions.assertTrue(matchRepository.findById(anyMatch.getId()).get().isDeleted());
        Assertions.assertEquals(anotherMatch.getId(),matchRepository.findById(anotherMatch.getId()).orElse(new Match()).getId());
    }

    @Test
    void addAndUpdateMatch() {
        matchRepository.save(anyMatch);
        Assertions.assertEquals("Any Match",matchRepository.findById(anyMatch.getId()).orElse(new Match()).getName());
        Match modifiedMatch = new Match(
                anyMatch.getId(),
                "Modified Match",
                anyMatch.getUserId(),
                LocalDateTime.now(ZoneOffset.UTC).plusDays(1),
                "Defensores del Chaco",
                LocalDateTime.now(ZoneOffset.UTC));
        matchRepository.save(modifiedMatch);
        Assertions.assertNotEquals("Any Match",matchRepository.findById(modifiedMatch.getId()).orElse(new Match()).getName());
        Assertions.assertEquals("Modified Match",matchRepository.findById(modifiedMatch.getId()).orElse(new Match()).getName());
    }

    @Test
    void updateUnknownMatch() {
        Match anyMatch = buildAMatch();
        matchRepository.save(anyMatch);
        matchRepository.save(buildAnotherMatch());
        Assertions.assertNull(matchRepository.findById(anotherMatch.getId()).orElse(null));
        Assertions.assertEquals(anyMatch.getId(),matchRepository.findById(anyMatch.getId()).orElse(new Match()).getId());
    }

    private Match buildAMatch() {
        User user =  new User("User", "User","0303456", "pp@g.com", "Password");

        Match anyMatch = new MatchBuilder()
                .setName("Any Match")
                .setUserId(UUID.randomUUID().toString())
                .setDateAndTime(LocalDateTime.now(ZoneOffset.UTC).plusDays(1))
                .setLocation("La Bombonera")
                .build();
        anyMatch.addPlayer(user);
        anyMatch.addPlayer(user);
        anyMatch.addPlayer(user);
        return anyMatch;
    }

    private Match buildAnotherMatch() {
        User user =  new User("User", "User","0303456", "pp@g.com", "Password");

        Match anotherMatch = new MatchBuilder()
                .setName("Another Match")
                .setUserId(UUID.randomUUID().toString())
                .setDateAndTime(LocalDateTime.now(ZoneOffset.UTC).plusDays(1))
                .setLocation("La Bombonera")
                .build();
        anotherMatch.addPlayer(user);
        anotherMatch.addPlayer(user);
        return anotherMatch;
    }
}
