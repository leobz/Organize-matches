package matches.organizer.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
                            .setDateAndTime(LocalDateTime.now(ZoneOffset.UTC).minusDays(1))
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
                    .setDateAndTime(LocalDateTime.now(ZoneOffset.UTC))
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
                    .setDateAndTime(LocalDateTime.now(ZoneOffset.UTC).plusDays(1))
                    .setLocation("La Bombonera")
                    .build();
        });
    }
}
