package katalon.com.sitemapservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateStatusSitemapDTO {

    private List<String> urls;

    private String status;
}


