package matches.organizer.domain;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class Player {

    private final String userId;
    private final String alias;

    private LocalDateTime confirmedAt;

    public Player(String userId, String alias) {
        this.userId = userId;
        this.alias = alias;
        this.confirmedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public String getUserId() {
        return userId;
    }

    public String getAlias() {
        return alias;
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
            playerJson.addProperty("userId", player.getUserId());
            playerJson.addProperty("confirmedAt", player.getConfirmedAt().toString());
            playerJson.addProperty("alias", player.getAlias());
            return playerJson;
        }
    }
}
