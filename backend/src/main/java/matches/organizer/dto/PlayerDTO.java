package matches.organizer.dto;

import matches.organizer.domain.Player;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerDTO {
    private UUID userId;
    private LocalDateTime confirmationDate;

    public PlayerDTO(Player player) {
        this.userId = player.getUserId();
        this.confirmationDate = player.getConfirmationDate();
    }

    public UUID getUserId() { return userId; }
    public LocalDateTime getConfirmationDate() { return confirmationDate; }
}
