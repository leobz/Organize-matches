package matches.organizer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.*;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Document
public class Match {

    //@Id
    @Indexed
    @Id
    @Hidden
    private String id;

    private String name;

    private String userId;
    @Schema(description = "Format yyyy-MM-ddTHH:mm:ss.SSSZ",
            format  = "yyyy-MM-ddTHH:mm:ss.sssZ",
            example= "2030-12-30T00:00:00.001Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dateAndTime;
    private String location;

    @Schema(description = "Format yyyy-MM-ddTHH:mm:ss.SSSZ",
            format  = "yyyy-MM-ddTHH:mm:ss.sssZ",
            example= "2030-12-30T00:00:00.001Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    @Hidden
    @DBRef(lazy = true)
    private List<Player> players;

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

    //Logger logger = LoggerFactory.getLogger(Match.class);

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

    public void addPlayer(User user) {
        if(getPlayers().size() >= 13) {
            //logger.error("NO MORE PLAYERS THAN 13 CAN BE SUBSCRIBED TO A MATCH");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot add player. The team is complete.");
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

    public void removePlayer(String playerId) {
        if(!players.stream().anyMatch((p) -> p.getUserId().compareTo(playerId) == 0))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Match: Cannot remove player. The user is not in the team.");
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
}