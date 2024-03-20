package katalon.com.sitemapservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class TestCase {
    @Id
    private String id;
    private List<String> urls;
    private String status;
    private String executionId;
    private String gptContent;
    private String testSpec;
}
