package katalon.com.sitemapservice.dto;

import lombok.Data;

@Data
public class UpdateRequestSitemapDTO {

    private String executionId;

    private String url;

    private String status;
}


