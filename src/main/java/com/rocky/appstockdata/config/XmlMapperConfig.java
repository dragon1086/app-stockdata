package com.rocky.appstockdata.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;

public class XmlMapperConfig {
    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }
}
