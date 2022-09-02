package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.MatchBuilder;
import matches.organizer.domain.Player;
import matches.organizer.domain.User;
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
    private final User user1 = new User("1", "User 1", "1111");
    private final User user2 = new User("2", "User 2", "2222");
    private final User user3 = new User("3", "User 3", "3333");
    private final User user4 = new User("4", "User 4", "4444");
    private final User user5 = new User("5", "User 5", "5555");


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
        Assertions.assertTrue(matchRepository.get(anyMatch.getId()).getPlayers().contains(user3));
        anyMatch.removeAllPlayers();
        anyMatch.addPlayer(user4, user4.getPhone(), user4.getEmail());
        anyMatch.addPlayer(user5, user5.getPhone(), user5.getEmail());
        final var modifiedPlayers = matchRepository.get(anyMatch.getId()).getPlayers();
        Assertions.assertFalse(modifiedPlayers.contains(user3));

        // TODO: Obtener de un repositorio de players un player por user ID
        Assertions.assertTrue(modifiedPlayers.contains(user5));

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
                .setDate(LocalDate.now().plusDays(1))
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anyMatch.addPlayer(user1, "1111-1111", "player1@gmail.com");
        anyMatch.addPlayer(user2, "2222-2222", "player2@gmail.com");
        anyMatch.addPlayer(user3, "3333-3333", "player3@gmail.com");
        return anyMatch;
    }

    private Match buildAnotherMatch() {
        Match anotherMatch = new MatchBuilder()
                .setName("Another Match")
                .setUserId(UUID.randomUUID())
                .setDate(LocalDate.now().plusDays(1))
                .setHour(LocalTime.now())
                .setLocation("La Bombonera")
                .build();
        anotherMatch.addPlayer(user4, "4444-4444", "player4@gmail.com");
        anotherMatch.addPlayer(user5, "5555-5555", "player5@gmail.com");
        return anotherMatch;
    }
}
