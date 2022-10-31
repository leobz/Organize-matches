package matches.organizer.storage;

import matches.organizer.domain.Match;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByCreatedAtAfter(LocalDateTime from);
    List<Match> findByDeletedFalse();
}
