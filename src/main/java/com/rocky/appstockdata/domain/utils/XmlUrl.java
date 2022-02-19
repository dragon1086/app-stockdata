package com.rocky.appstockdata.domain.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JacksonXmlRootElement(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrl {
    private static final String BASE_URL = "http://stocksimulation.kr";

    @JacksonXmlProperty(localName = "loc", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String loc;

    @JacksonXmlProperty(localName = "lastmod", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String lastmod;

    @JacksonXmlProperty(localName = "changefreq", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String changefreq;

    @JacksonXmlProperty(localName = "priority", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
    private String priority;

    public XmlUrl(String loc) {
        this.loc = BASE_URL + loc;
        this.lastmod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.changefreq = "monthly";
        this.priority = "0.5";
    }
}
