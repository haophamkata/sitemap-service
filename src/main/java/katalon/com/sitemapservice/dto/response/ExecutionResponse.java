package katalon.com.sitemapservice.dto.response;

import java.util.List;

public class ExecutionResponse {
    private List<Execution> content;

    // Getters and setters
    public List<Execution> getContent() {
        return content;
    }

    public void setContent(List<Execution> content) {
        this.content = content;
    }
}