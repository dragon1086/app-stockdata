package com.rocky.appstockdata.domain.utils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@JacksonXmlRootElement(localName = "url", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class XmlUrl {
    private static final String BASE_URL = "http://stocksimulation.kr";

    @JacksonXmlProperty(localName = "loc")
    private String loc;

    @JacksonXmlProperty(localName = "lastmod")
    private String lastmod;

    @JacksonXmlProperty(localName = "changefreq")
    private String changefreq = "daily";

    @JacksonXmlProperty(localName = "priority")
    private String priority = "0.5";

    public XmlUrl(String loc) {
        this.loc = BASE_URL + loc;
        this.lastmod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
