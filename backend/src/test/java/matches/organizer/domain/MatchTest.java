package matches.organizer.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                    new MatchBuilder()
                            .setName("Another Match")
                            .setUserId(UUID.randomUUID())
                            .setDate(LocalDate.now(ZoneOffset.UTC).minusDays(1))
                            .setHour(LocalTime.now(ZoneOffset.UTC))
                            .setLocation("La Bombonera")
                            .build()
                );
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void createMatchInPresentAndFail() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
            new MatchBuilder()
                    .setName("Another Match")
                    .setUserId(UUID.randomUUID())
                    .setDate(LocalDate.now(ZoneOffset.UTC))
                    .setHour(LocalTime.now(ZoneOffset.UTC))
                    .setLocation("La Bombonera")
                    .build()
        );

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
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
