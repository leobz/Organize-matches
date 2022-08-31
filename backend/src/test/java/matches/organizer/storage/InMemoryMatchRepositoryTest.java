package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes=InMemoryMatchRepository.class)
class InMemoryMatchRepositoryTest {

    @Autowired
    private MatchRepository matchRepository;
    private final Player player1 = new Player("1");
    private final Player player2 = new Player("2");
    private final Player player3 = new Player("3");
    private final Player player4 = new Player("4");
    private final Player player5 = new Player("5");

    @Test
    void addAndGetMatch() {
        matchRepository.add(buildAMatch());
        matchRepository.add(buildAnotherMatch());
        Assertions.assertEquals("1",matchRepository.get("1").getId());
        Assertions.assertEquals("2",matchRepository.get("2").getId());
    }

    @Test
    void addAndRemoveMatch() {
        Match anyMatch = buildAMatch();
        matchRepository.add(anyMatch);
        matchRepository.add(buildAnotherMatch());
        matchRepository.remove(anyMatch);
        Assertions.assertNull(matchRepository.get("1"));
        Assertions.assertEquals("2",matchRepository.get("2").getId());
    }

    @Test
    void addAndUpdateMatch() {
        Match anyMatch = buildAMatch();
        matchRepository.add(anyMatch);
        Assertions.assertTrue(matchRepository.get("1").getPlayers().contains(player3));
        anyMatch.setPlayers( List.of(player4, player5) );
        final var modifiedPlayers = matchRepository.get("1").getPlayers();
        Assertions.assertFalse(modifiedPlayers.contains(player3));
        Assertions.assertTrue(modifiedPlayers.contains(player5));
        matchRepository.update(anyMatch);
    }

    @Test
    void updateUnknownMatch() {
        Match anyMatch = buildAMatch();
        matchRepository.add(anyMatch);
        matchRepository.update(buildAnotherMatch());
        Assertions.assertNull(matchRepository.get("2"));
        Assertions.assertEquals("1",matchRepository.get("1").getId());
    }

    private Match buildAMatch() {
        var anyMatch = new Match();
        anyMatch.setId("1");
        anyMatch.getPlayers().add(player1);
        anyMatch.getPlayers().add(player2);
        anyMatch.getPlayers().add(player3);
        return anyMatch;
    }

    private Match buildAnotherMatch() {
        Match anotherMatch = new Match();
        anotherMatch.setId("2");
        anotherMatch.getPlayers().add(player4);
        anotherMatch.getPlayers().add(player5);
        return anotherMatch;
    }

}
