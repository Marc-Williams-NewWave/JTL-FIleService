package nwt.jtlserver.com.repository;

import nwt.jtlserver.com.domain.PerformanceResults;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the User entity.
 */
public interface JTLRepository extends MongoRepository<PerformanceResults, String> {

}
