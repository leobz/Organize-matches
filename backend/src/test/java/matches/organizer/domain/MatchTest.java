package matches.organizer.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class MatchTest {

    @Test
    void createMatchInPastAndFail() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
                    Match anotherMatch = new MatchBuilder()
                            .setName("Another Match")
                            .setUserId(UUID.randomUUID())
                            .setDate(LocalDate.now().minusDays(1))
                            .setHour(LocalTime.now())
                            .setLocation("La Bombonera")
                            .build();
                });

        assertTrue(exception.getMessage().contains("MatchBuilder: date and hour is in the past"));
    }

    @Test
    void createMatchInPresentAndFail() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            Match anotherMatch = new MatchBuilder()
                    .setName("Another Match")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now())
                    .setHour(LocalTime.now())
                    .setLocation("La Bombonera")
                    .build();
        });

        assertTrue(exception.getMessage().contains("MatchBuilder: date and hour is in the past"));
    }


    @Test
    void createMatchInFutureAndSuccess() {
        assertDoesNotThrow(() -> {
            Match anotherMatch = new MatchBuilder()
                    .setName("Another Match")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now().plusDays(1))
                    .setHour(LocalTime.now())
                    .setLocation("La Bombonera")
                    .build();
        });
    }
}
