package nwt.jtlserver.com.repository;

import nwt.jtlserver.com.domain.PerformanceResults;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PerformanceRepository extends MongoRepository<PerformanceResults, String>{

}
