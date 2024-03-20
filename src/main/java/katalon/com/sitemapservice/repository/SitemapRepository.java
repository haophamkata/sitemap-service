package katalon.com.sitemapservice.repository;

import katalon.com.sitemapservice.model.Sitemap;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author haoph
 *
 */
@Repository
public interface SitemapRepository extends MongoRepository<Sitemap, String> {


}
