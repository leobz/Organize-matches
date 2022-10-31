package matches.organizer.storage;

import matches.organizer.domain.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StatisticRepository extends MongoRepository<Statistic, String> {

    Long countStatisticByStatisticType(int statisticType);


}
