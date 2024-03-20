package katalon.com.sitemapservice.api;


import io.swagger.annotations.ApiOperation;
import katalon.com.sitemapservice.dto.SitemapDTO;
import katalon.com.sitemapservice.dto.UrlDTO;
import katalon.com.sitemapservice.dto.UpdateRequestSitemapDTO;
import katalon.com.sitemapservice.dto.response.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public interface SitemapAPI {

    String SITEMAP = "/sitemap";
    String URL = "/url";


    @ApiOperation(value = "Order products by customer", response = Response.class)
    @PostMapping(path = SITEMAP)
    Response add(@RequestBody SitemapDTO dto);

    @ApiOperation(value = "Update sitemap by customer", response = Response.class)
    @PutMapping(path = SITEMAP)
    Response update(@RequestBody List<UpdateRequestSitemapDTO> dto);


    @ApiOperation(value = "Get one", response = Response.class)
    @GetMapping(path = SITEMAP)
    Response findById();

    @ApiOperation(value = "Order products by customer", response = Response.class)
    @PostMapping(path = URL)
    void registerUrl(@RequestBody UrlDTO url);

    @ApiOperation(value = "Get one", response = Response.class)
    @GetMapping(path = URL + "/{url}")
    Response findUrl(String url);

}
