package com.rocky.appstockdata.adaptor.web.in;

import com.rocky.appstockdata.application.service.SitemapService;
import com.rocky.appstockdata.domain.utils.XmlUrlSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class WebUtilApiV1 {
    private final SitemapService sitemapService;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    public WebUtilApiV1(SitemapService sitemapService,
                        RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.sitemapService = sitemapService;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @GetMapping(value = "/sitemap.xml", produces = MediaType.TEXT_XML_VALUE)
    @ResponseBody
    public XmlUrlSet sitemap() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        List<String> urls = new ArrayList<>();
        String baseUrl = "https://stocksimulation.kr";

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();

            if (mappingInfo.getPathPatternsCondition() != null) {
                mappingInfo.getPathPatternsCondition().getPatterns()
                        .forEach(pattern -> urls.add(baseUrl + pattern.getPatternString()));
            }
            else if (mappingInfo.getPatternsCondition() != null) {
                mappingInfo.getPatternsCondition().getPatterns()
                        .forEach(pattern -> urls.add(baseUrl + pattern));
            }
        }

        return sitemapService.createSitemap(urls);
    }


    @RequestMapping(value = "/robots.txt")
    @ResponseBody
    public String robots() {
        return "User-agent: *\n" +
                "Disallow: /company\n" +
                "Disallow: /resources/\n" +
                "Sitemap: https://stocksimulation.kr/sitemap.xml";
    }

    @RequestMapping(value = "/ads.txt", method = RequestMethod.GET)
    @ResponseBody
    public String getAdsFile(HttpServletResponse response) throws IOException {
        String fileName = "ads.txt";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        return "google.com, pub-3126725853648403, DIRECT, f08c47fec0942fa0";
    }
}
