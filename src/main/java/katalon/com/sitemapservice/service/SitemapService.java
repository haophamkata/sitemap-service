package katalon.com.sitemapservice.service;


import katalon.com.sitemapservice.dto.SitemapDTO;
import katalon.com.sitemapservice.dto.TestCaseDTO;
import katalon.com.sitemapservice.model.Sitemap;
import katalon.com.sitemapservice.model.TestCase;

import java.util.List;

public interface SitemapService {

   Sitemap add(SitemapDTO dto);

   Sitemap updateStatus(List<String> urls, String status, String id);

   Sitemap findById(String id);

   String findUrl(String url);

   void registerUrl(String url);

    TestCase createTestCase(TestCaseDTO dto);

   void updateExecutionIdByTestCaseId(String id, String executionId);


}
