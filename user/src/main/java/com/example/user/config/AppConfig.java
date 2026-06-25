package com.example.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "app-info")
@Data
public class AppConfig {

    private String name;
    private String version;
    private String description;
    private Map<String, String> developer;
    private Map<String, String> contact;
}
