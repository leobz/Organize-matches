package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class InMemoryMatchRepositoryTest {

    private MatchRepository matchRepository;
    private Match anyMatch;
    private Match anotherMatch;

    @BeforeEach
    void setUp() {
        matchRepository = new InMemoryMatchRepository();
        anyMatch = buildAMatch();
        anotherMatch = buildAnotherMatch();
    }

    @Test
    void addAndGetMatch() {
        matchRepository.add(anyMatch);
        matchRepository.add(anotherMatch);
        Assertions.assertEquals(anyMatch.getId(),matchRepository.get(anyMatch.getId()).getId());
        Assertions.assertEquals(anotherMatch.getId(),matchRepository.get(anotherMatch.getId()).getId());
    }

    @Test
    void addAndRemoveMatch() {
        matchRepository.add(anyMatch);
        matchRepository.add(anotherMatch);
        matchRepository.remove(anyMatch);
        Assertions.assertTrue(matchRepository.get(anyMatch.getId()).isDeleted());
        Assertions.assertEquals(anotherMatch.getId(),matchRepository.get(anotherMatch.getId()).getId());
    }

    @Test
    void addAndUpdateMatch() {
        matchRepository.add(anyMatch);
        Assertions.assertEquals("Any Match",matchRepository.get(anyMatch.getId()).getName());
        Match modifiedMatch = new Match(
                anyMatch.getId(),
                "Modified Match",
                anyMatch.getUserId(),
                LocalDateTime.now(ZoneOffset.UTC).plusDays(1),
                "Defensores del Chaco",
                LocalDateTime.now(ZoneOffset.UTC));
        matchRepository.update(modifiedMatch);
        Assertions.assertNotEquals("Any Match",matchRepository.get(modifiedMatch.getId()).getName());
        Assertions.assertEquals("Modified Match",matchRepository.get(modifiedMatch.getId()).getName());
    }

    @Test
    void updateUnknownMatch() {
        Match anyMatch = buildAMatch();
        matchRepository.add(anyMatch);
        matchRepository.update(buildAnotherMatch());
        Assertions.assertNull(matchRepository.get(anotherMatch.getId()));
        Assertions.assertEquals(anyMatch.getId(),matchRepository.get(anyMatch.getId()).getId());
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
