package matches.organizer.domain;

import com.google.gson.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Document
public class Player {

    private final String userId;
    private final String alias;

    @Indexed(name = "indexConfirmedAt", expireAfterSeconds = 60*60*2, partialFilter = "{ clearable : {$exists : true } }" )
    private LocalDateTime confirmedAt;

    private boolean clearable;

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

    public void setClearable(boolean clearable) { this.clearable = clearable; }

    //public String getClearable() {        return clearable;    }



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
