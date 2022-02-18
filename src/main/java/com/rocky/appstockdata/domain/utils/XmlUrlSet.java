package com.rocky.appstockdata.domain.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrlSet {
    @JacksonXmlProperty(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<XmlUrl> xmlUrls = new ArrayList<>();

    public void addUrl(XmlUrl xmlUrl) {
        xmlUrls.add(xmlUrl);
    }
}
