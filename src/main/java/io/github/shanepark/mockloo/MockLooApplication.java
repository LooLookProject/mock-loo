package io.github.shanepark.mockloo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
public class MockLooApplication implements ApplicationListener<ContextRefreshedEvent> {

    private final LooSimulator looSimulator;

    public static void main(String[] args) {
        SpringApplication.run(MockLooApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        looSimulator.simulate();
    }
}
