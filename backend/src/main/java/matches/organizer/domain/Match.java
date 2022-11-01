package matches.organizer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document
public class Match {

    @Indexed
    @Id
    @Hidden
    private String id;
    @NotBlank
    private String name;

    private String userId;
    @Schema(description = "Format yyyy-MM-ddTHH:mm:ss.SSSZ",
            format  = "yyyy-MM-ddTHH:mm:ss.sssZ",
            example= "2030-12-30T00:00:00.001Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateAndTime;
    @NotBlank
    private String location;

    @Schema(description = "Format yyyy-MM-ddTHH:mm:ss.SSSZ",
            format  = "yyyy-MM-ddTHH:mm:ss.sssZ",
            example= "2030-12-30T00:00:00.001Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    @Hidden
    private List<Player> players;
    @Hidden
    private boolean deleted = false;

    public Match(){}

    public Match(String id, String name, String userId, LocalDateTime dateAndTime, String location, LocalDateTime createdAt){
       this.id = id;
       this.name = name;
       this.userId = userId;
       this.dateAndTime = dateAndTime;
       this.location = location;
       this.createdAt = createdAt;
       this.players = new ArrayList<>();
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime dateTime) {
        this.createdAt = dateTime;
    }

    public List<Player> getPlayers() { return players; }

    public List<Player> getStartingPlayers() {
        return players.stream().limit(10).collect(Collectors.toList());
    }

    public List<Player> getSubstitutePlayers() {
        return players.stream().skip(10).limit(3).collect(Collectors.toList());
    }

    public void addPlayer(User user) throws AddPlayerException {
        if(getPlayers().size() >= 13) {
            throw new AddPlayerException("Match: Cannot add player. The team is complete.");
        }
        players.add(new Player(user.getId(), user.getAlias()));
    }

    public String toJsonString() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Match.class, new MatchSerializer())
                .create();
        return gson.toJson(this);
    }

    public static JsonArray getPlayersJsonArray(List<Player> players) {
        JsonArray matchesArray = new JsonArray();
        players.forEach(player -> matchesArray.add(JsonParser.parseString(player.toJsonString())));
        return matchesArray;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public void removePlayer(String playerId) throws RemovePlayerException{
        if(players.stream().noneMatch((p) -> p.getUserId().compareTo(playerId) == 0))
            throw new RemovePlayerException("Match: Cannot remove player. The user is not in the team.");
        players.remove(players.stream().filter((p) -> p.getUserId().compareTo(playerId) == 0).findFirst().get());
    }

    static class MatchSerializer implements JsonSerializer<Match> {
        @Override
        public JsonElement serialize(Match match, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject matchJson = new JsonObject();
            matchJson.addProperty("id", match.getId());
            matchJson.addProperty("name", match.getName());
            matchJson.addProperty("userId", match.getUserId());
            matchJson.addProperty("dateAndTime", match.getDateAndTime().toString());
            matchJson.addProperty("location", match.getLocation());
            matchJson.add("startingPlayers", getPlayersJsonArray(match.getStartingPlayers()));
            matchJson.add("substitutePlayers", getPlayersJsonArray(match.getSubstitutePlayers()));
            matchJson.addProperty("createdAt", match.getCreatedAt().toString());
            return matchJson;
        }
    }
    
    public static class AddPlayerException extends Throwable {
        public AddPlayerException(String s) {
        }
    }

    public static class RemovePlayerException extends Throwable {
        public RemovePlayerException(String s) {
        }
    }

    @Override
    public String toString() {
        return "\nMatch: " + name + "\nLocation: " + location + "\nDate and time: " + dateAndTime + "\n\n";
    }
}