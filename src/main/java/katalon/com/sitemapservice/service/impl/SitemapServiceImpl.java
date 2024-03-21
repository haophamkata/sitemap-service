package katalon.com.sitemapservice.service.impl;


import katalon.com.sitemapservice.dto.*;
import katalon.com.sitemapservice.model.Sitemap;
import katalon.com.sitemapservice.model.TestCase;
import katalon.com.sitemapservice.model.WebsiteUrl;
import katalon.com.sitemapservice.repository.TestCaseRepository;
import katalon.com.sitemapservice.repository.WebsiteUrlRepository;
import katalon.com.sitemapservice.service.SitemapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import katalon.com.sitemapservice.repository.SitemapRepository;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SitemapServiceImpl implements SitemapService {


    @Autowired
    private SitemapRepository sitemapRepository;


    @Autowired
    private WebsiteUrlRepository websiteUrlRepository;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Override
    public Sitemap add(SitemapDTO dto) {
        Sitemap sitemap = new Sitemap();
        sitemap.setName(dto.getName());
        sitemap.setChildren(dto.getChildren());
        sitemap.setType(dto.getType());
        sitemap.setUrl(dto.getUrl());
        sitemap.setStatus(dto.getStatus());
        websiteUrlRepository.findByUrl(dto.getUrl()).ifPresent(websiteUrl -> {
            websiteUrl.setStatus("DONE");
            websiteUrlRepository.save(websiteUrl);
        });
        return sitemapRepository.save(sitemap);
    }

    @Override
    public Sitemap updateStatus(List<String> urls, String status, String id) {
        Optional<Sitemap> sitemap = sitemapRepository.findById(id);
        if(!sitemap.isPresent()) {
            return null;
        }
        urls.forEach(url -> {
            if(status != null) {
                log.info("Update status of url: " + url + " to " + status);
                sitemap.get().updateStatusByUrl(url, status);
            }
            sitemapRepository.save(sitemap.get());
        });
       return sitemap.get();
    }



    @Override
    public Sitemap findById(String id) {
        Optional<Sitemap> sitemap = sitemapRepository.findById(id);
        return sitemap.orElse(null);
    }

    @Override
    public String findUrl(String url) {
        return websiteUrlRepository.findById(url).map(WebsiteUrl::getUrl).orElse(null);
    }

    @Override
    public void registerUrl(String url) {
        if(websiteUrlRepository.findByUrl(url).isPresent()){
            return;
        }
        WebsiteUrl websiteUrl = new WebsiteUrl();
        websiteUrl.setUrl(url);
        websiteUrl.setStatus("PROCESSING");
        websiteUrlRepository.save(websiteUrl);
    }

    @Override
    public TestCase createTestCase(TestCaseDTO dto) {
        TestCase testCase = new TestCase();
        testCase.setUrls(dto.getUrls());
        testCase.setSiteMapId(dto.getSiteMapId());
        testCase.setTestSpec(dto.getTestSpec());
        testCase.setStatus("RUNNING");
        return testCaseRepository.save(testCase);
    }

    @Override
    public void updateExecutionIdByTestCaseId(String id, String executionId) {
        testCaseRepository.findById(id).ifPresent(testCase -> {
            testCase.setExecutionId(executionId);
            testCaseRepository.save(testCase);
        });
    }
}