package matches.organizer.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class MatchTest {

    @Test
    void createMatchInPastAndFail() {
        Exception exception = assertThrows(RuntimeException.class, () ->
                    new MatchBuilder()
                            .setName("Another Match")
                            .setUserId(UUID.randomUUID())
                            .setDate(LocalDate.now(ZoneOffset.UTC).minusDays(1))
                            .setHour(LocalTime.now(ZoneOffset.UTC))
                            .setLocation("La Bombonera")
                            .build()
                );

        assertTrue(exception.getMessage().contains("MatchBuilder: date and hour is in the past"));
    }

    @Test
    void createMatchInPresentAndFail() {
        Exception exception = assertThrows(RuntimeException.class, () ->
            new MatchBuilder()
                    .setName("Another Match")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now(ZoneOffset.UTC))
                    .setHour(LocalTime.now(ZoneOffset.UTC))
                    .setLocation("La Bombonera")
                    .build()
        );

        assertTrue(exception.getMessage().contains("MatchBuilder: date and hour is in the past"));
    }


    @Test
    void createMatchInFutureAndSuccess() {
        assertDoesNotThrow(() -> {
            new MatchBuilder()
                    .setName("Another Match")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now(ZoneOffset.UTC).plusDays(1))
                    .setHour(LocalTime.now(ZoneOffset.UTC))
                    .setLocation("La Bombonera")
                    .build();
        });
    }
}
