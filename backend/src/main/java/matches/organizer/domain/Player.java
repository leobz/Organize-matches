package matches.organizer.domain;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Player {

    private final UUID userId;

    private LocalDateTime confirmedAt;

    public Player(UUID userId) {
        this.userId = userId;
        this.confirmedAt = LocalDateTime.now(ZoneOffset.UTC);
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

    public String toJsonString() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Player.class, new Player.PlayerSerializer())
                .create();
        return gson.toJson(this);
    }

    static class PlayerSerializer implements JsonSerializer<Player> {
        @Override
        public JsonElement serialize(Player player, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("userId", player.getUserId().toString());
            playerJson.addProperty("confirmedAt", player.getConfirmedAt().toString());
            return playerJson;
        }
    }
}
