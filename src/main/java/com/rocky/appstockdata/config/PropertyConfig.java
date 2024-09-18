package com.rocky.appstockdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySource("classpath:properties/env-local.properties")
public class PropertyConfig {
}
