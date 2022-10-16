package matches.organizer.storage;

import matches.organizer.domain.Match;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface MatchRepository extends MongoRepository<Match, String> {

}
