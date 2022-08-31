package matches.organizer.dto;

import matches.organizer.domain.Player;

import java.util.UUID;

public class PlayerDTO {
    private UUID id;
    private String name;

    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
    }

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }
}
