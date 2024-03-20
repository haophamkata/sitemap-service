package katalon.com.sitemapservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Sitemap {
    @Id
    private String id;
    private String name;
    private List<Sitemap> children;
    private String type;
    private String url;
    private String status;

    public void updateStatusByUrl(String urlToUpdate, String newStatus) {
        // Update status if url matches
        if (url != null && url.equals(urlToUpdate)) {
            status = newStatus;
        }

        // Recursively call updateStatusByUrl for children
        if (children != null) {
            for (Sitemap child : children) {
                child.updateStatusByUrl(urlToUpdate, newStatus);
            }
        }
    }

}
