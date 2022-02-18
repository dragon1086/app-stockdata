package com.rocky.appstockdata.application.service;

import com.rocky.appstockdata.domain.SiteMap;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SiteMapService {
    public static final String BASE_URL = "http://www.site.com";
    public static final String BEGIN_DOC = "<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">";
    public static final String END_DOC = "</urlset>";
    public static final String CHANGEFREQ_ALWAYS = "always";
    public static final String CHANGEFREQ_HOURLY = "hourly";
    public static final String CHANGEFREQ_DAILY = "daily";
    public static final String CHANGEFREQ_WEEKLY = "weekly";
    public static final String CHANGEFREQ_MONTHLY = "monthly";
    public static final String CHANGEFREQ_YEARLY = "yearly";
    public static final String CHANGEFREQ_NEVER = "never";

    public String getSystemicSiteMap(){
        Date now = new Date();
        StringBuffer sb = new StringBuffer();
        sb.append(BEGIN_DOC);
        sb.append(new SiteMap(BASE_URL, now, CHANGEFREQ_MONTHLY, "1.0"));
        sb.append(END_DOC);
        return sb.toString();
    }
}
