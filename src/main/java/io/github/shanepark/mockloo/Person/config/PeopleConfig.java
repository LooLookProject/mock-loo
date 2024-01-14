package io.github.shanepark.mockloo.Person.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "people")
public record PeopleConfig(
        int count,
        int useToiletSeconds
) {

}
