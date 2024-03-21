package katalon.com.sitemapservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import katalon.com.sitemapservice.model.Sitemap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestCaseDTO {
    private String testSpec;
    private List<String> urls;
    private String siteMapId;
}


