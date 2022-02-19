package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.domain.utils.XmlUrl;
import com.rocky.appstockdata.domain.utils.XmlUrlSet;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class SitemapService {
    private static final List<String> BLACK_LIST = asList("/3month-to-today/{id}",
                                                        "/6month-to-3month-ago/{id}",
                                                        "/9month-to-6month-ago/{id}",
                                                        "/12month-to-9month-ago/{id}");

    public XmlUrlSet createSitemap(List<String> urls) {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        for (String url : urls) {
            if(!BLACK_LIST.contains(url)){
                xmlUrlSet.addUrl(new XmlUrl(url));
            }
        }
        return xmlUrlSet;
    }
}
