package matches.organizer.domain;

import matches.organizer.dto.PlayerDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class Player {

    private UUID userId;

    private LocalDateTime confirmationDate;

    public Player(UUID userId) {
        this.userId = userId;
        this.confirmationDate = LocalDateTime.now();
    }

    public UUID getUserId() { return userId; }

    public LocalDateTime getConfirmationDate() { return confirmationDate; }

    public PlayerDTO getDto() {
        return new PlayerDTO(this);
    }
}
