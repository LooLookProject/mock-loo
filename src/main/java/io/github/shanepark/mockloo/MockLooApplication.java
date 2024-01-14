package io.github.shanepark.mockloo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MockLooApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockLooApplication.class, args);
    }

}
