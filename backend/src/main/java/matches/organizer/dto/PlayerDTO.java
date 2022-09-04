package matches.organizer.dto;

import matches.organizer.domain.Player;

import java.time.LocalDateTime;
import java.util.UUID;

public class PlayerDTO {
    private UUID userId;
    private LocalDateTime confirmedAt;

    public PlayerDTO(Player player) {
        this.userId = player.getUserId();
        this.confirmedAt = player.getConfirmedAt();
    }

    public UUID getUserId() { return userId; }
    public String getConfirmedAt() { return confirmedAt.toString(); }
}
