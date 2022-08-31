package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class InMemoryMatchRepositoryTest {

    private MatchRepository matchRepository;
    private Match anyMatch;
    private Match anotherMatch;
    private final Player player1 = new Player("1");
    private final Player player2 = new Player("2");
    private final Player player3 = new Player("3");
    private final Player player4 = new Player("4");
    private final Player player5 = new Player("5");


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
        Assertions.assertNull(matchRepository.get(anyMatch.getId()));
        Assertions.assertEquals(anotherMatch.getId(),matchRepository.get(anotherMatch.getId()).getId());
    }

    @Test
    void addAndUpdateMatch() {
        matchRepository.add(anyMatch);
        Assertions.assertTrue(matchRepository.get(anyMatch.getId()).getPlayers().contains(player3));
        anyMatch.removeAllPlayers();
        anyMatch.addPlayer(player4);
        anyMatch.addPlayer(player5);
        final var modifiedPlayers = matchRepository.get(anyMatch.getId()).getPlayers();
        Assertions.assertFalse(modifiedPlayers.contains(player3));
        Assertions.assertTrue(modifiedPlayers.contains(player5));
        matchRepository.update(anyMatch);
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
        Match anyMatch = new MatchBuilder()
                .setName("Any Match")
                .setUserId(UUID.randomUUID())
                .setDate(LocalDate.now())
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anyMatch.getPlayers().add(player1);
        anyMatch.getPlayers().add(player2);
        anyMatch.getPlayers().add(player3);
        return anyMatch;
    }

    private Match buildAnotherMatch() {
        Match anotherMatch = new MatchBuilder()
                .setName("Another Match")
                .setUserId(UUID.randomUUID())
                .setDate(LocalDate.now())
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anotherMatch.getPlayers().add(player4);
        anotherMatch.getPlayers().add(player5);
        return anotherMatch;
    }
}
