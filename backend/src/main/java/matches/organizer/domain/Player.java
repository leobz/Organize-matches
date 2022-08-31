package matches.organizer.domain;

import matches.organizer.dto.PlayerDTO;

import java.util.UUID;

public class Player {

    private UUID id = UUID.randomUUID();

    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public PlayerDTO getDto() {
        return new PlayerDTO(this);
    }
}
