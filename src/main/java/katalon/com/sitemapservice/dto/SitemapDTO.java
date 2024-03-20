package katalon.com.sitemapservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import katalon.com.sitemapservice.model.Sitemap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SitemapDTO {
    private String id;
    private String name;
    private List<Sitemap> children;
    private String type;
    private String url;
    private String status;

}


