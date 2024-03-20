package katalon.com.sitemapservice.repository;

import katalon.com.sitemapservice.model.Sitemap;
import katalon.com.sitemapservice.model.WebsiteUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author haoph
 *
 */
@Repository
public interface WebsiteUrlRepository extends MongoRepository<WebsiteUrl, String> {

    Optional<WebsiteUrl> findByUrl(String url);
}
