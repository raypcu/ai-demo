package dev.ray;

import dev.ray.functions.WeatherConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class DadJokesApplication {

    public static void main(String[] args) {
        SpringApplication.run(DadJokesApplication.class, args);
    }

}
