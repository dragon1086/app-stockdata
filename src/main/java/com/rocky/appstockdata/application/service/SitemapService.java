package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.domain.utils.XmlUrl;
import com.rocky.appstockdata.domain.utils.XmlUrlSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SitemapService {
    // 여기서 loop 돌면서 모든 XmlUrl을 XmlUrlSet에 담는다.
    public XmlUrlSet createSitemap(List<String> urls) {
        XmlUrlSet xmlUrlSet = new XmlUrlSet();
        for (String url : urls) {
            xmlUrlSet.addUrl(new XmlUrl(url));

        }
        return xmlUrlSet;
    }
}
