package io.github.shanepark.mockloo.loo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "loo")
public record LooConfig(
        List<String> ids
) {

}
