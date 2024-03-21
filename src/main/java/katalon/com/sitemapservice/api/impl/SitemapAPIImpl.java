package katalon.com.sitemapservice.api.impl;


import katalon.com.sitemapservice.api.SitemapAPI;
import katalon.com.sitemapservice.dto.SitemapDTO;
import katalon.com.sitemapservice.dto.TestCaseDTO;
import katalon.com.sitemapservice.dto.UrlDTO;
import katalon.com.sitemapservice.dto.UpdateStatusSitemapDTO;
import katalon.com.sitemapservice.dto.response.Response;
import katalon.com.sitemapservice.service.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1")
public class SitemapAPIImpl implements SitemapAPI {

    @Autowired
    SitemapService sitemapService;

    @Override
    public Response add(SitemapDTO dto) {
        return new Response(sitemapService.add(dto));
    }


    @Override
    public Response findById(String id) {
        return new Response(sitemapService.findById(id));
    }

    @Override
    public void registerUrl(UrlDTO url) {
         sitemapService.registerUrl(url.getUrl());
    }

    @Override
    public Response findUrl(String url) {
        return new Response(sitemapService.findUrl(url));
    }

    @Override
    public Response createTestCase(TestCaseDTO dto) {
        return new Response(sitemapService.createTestCase(dto));
    }

    @Override
    public void updateExecutionIdByTestCaseId(String id, String executionId) {
        sitemapService.updateExecutionIdByTestCaseId(id, executionId);
    }
}
