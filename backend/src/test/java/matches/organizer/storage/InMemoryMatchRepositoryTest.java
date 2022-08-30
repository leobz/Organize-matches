package matches.organizer.storage;

import matches.organizer.domain.Match;
import matches.organizer.domain.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        var anyMatch = new Match();
        anyMatch.setId("1");
        anyMatch.getPlayers().add(player1);
        anyMatch.getPlayers().add(player2);
        anyMatch.getPlayers().add(player3);
        matchRepository.add(anyMatch);
        var anotherMatch = new Match();
        anyMatch.setId("2");
        anotherMatch.getPlayers().add(player4);
        anotherMatch.getPlayers().add(player5);
        matchRepository.add(anotherMatch);
        Assertions.assertEquals("2",matchRepository.get("2").getId());
    }

}
