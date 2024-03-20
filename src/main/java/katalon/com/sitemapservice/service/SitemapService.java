package katalon.com.sitemapservice.service;


import katalon.com.sitemapservice.dto.SitemapDTO;
import katalon.com.sitemapservice.dto.UpdateRequestSitemapDTO;
import katalon.com.sitemapservice.model.Sitemap;

import java.util.List;

public interface SitemapService {

   Sitemap add(SitemapDTO dto);

   Sitemap update(List<UpdateRequestSitemapDTO> dtos);

   Sitemap findOne();

   String findUrl(String url);

    void registerUrl(String url);

    
}
