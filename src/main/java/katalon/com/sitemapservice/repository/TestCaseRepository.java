package katalon.com.sitemapservice.repository;

import katalon.com.sitemapservice.model.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author haoph
 *
 */
@Repository
public interface TestCaseRepository extends MongoRepository<TestCase, String> {

    List<TestCase> findAllByStatusAndExecutionIdNotNull(String status);
}
