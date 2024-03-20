package katalon.com.sitemapservice.service.impl;


import katalon.com.sitemapservice.dto.SitemapDTO;
import katalon.com.sitemapservice.dto.UpdateRequestSitemapDTO;
import katalon.com.sitemapservice.model.Sitemap;
import katalon.com.sitemapservice.model.WebsiteUrl;
import katalon.com.sitemapservice.repository.WebsiteUrlRepository;
import katalon.com.sitemapservice.service.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import katalon.com.sitemapservice.repository.SitemapRepository;

import java.util.List;
import java.util.Optional;


@Service
public class SitemapServiceImpl implements SitemapService {


    @Autowired
    private SitemapRepository sitemapRepository;


    @Autowired
    private WebsiteUrlRepository websiteUrlRepository;


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
    public Sitemap update(List<UpdateRequestSitemapDTO> dtos) {
        Optional<Sitemap> sitemap = sitemapRepository.findAll().stream().findFirst();
        if(!sitemap.isPresent()) {
            return null;
        }
        dtos.forEach(dto -> {
            if(dto.getStatus() != null) {
                sitemap.get().updateStatusByUrl(dto.getUrl(), dto.getStatus());
            }
            if(dto.getExecutionId() != null) {
                sitemap.get().updateExecutionIdByUrl(dto.getUrl(), dto.getExecutionId());
            }
            sitemapRepository.save(sitemap.get());
        });
       return sitemap.get();
    }



    @Override
    public Sitemap findOne() {
        Optional<Sitemap> sitemap = sitemapRepository.findAll().stream().findFirst();
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
}