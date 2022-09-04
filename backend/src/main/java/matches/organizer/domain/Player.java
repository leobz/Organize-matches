package matches.organizer.domain;

import matches.organizer.dto.PlayerDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class Player {

    private final UUID userId;

    private LocalDateTime confirmedAt;

    public Player(UUID userId) {
        this.userId = userId;
        this.confirmedAt = LocalDateTime.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public PlayerDTO getDto() {
        return new PlayerDTO(this);
    }
}
