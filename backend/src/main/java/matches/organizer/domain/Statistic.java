package matches.organizer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Document
public class Statistic {

    @Id
    private String id;

    private int statisticType; // 1 - for matches , 2 - for players.


    @Indexed(name = "indexConfirmedAt", expireAfterSeconds = 7200)
    private LocalDateTime confirmedAt;


    public Statistic(int statisticType) {
        this.id = UUID.randomUUID().toString();
        this.statisticType = statisticType;
        this.confirmedAt = LocalDateTime.now(ZoneOffset.UTC);
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(int statisticType) {
        this.statisticType = statisticType;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
