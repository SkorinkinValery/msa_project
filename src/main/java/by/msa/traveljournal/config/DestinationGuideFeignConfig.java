package by.msa.traveljournal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

@Configuration
public class DestinationGuideFeignConfig {

    @Bean
    Logger.Level destinationGuideFeignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    Logger destinationGuideFeignLogger() {
        return new OneLineFeignLogger();
    }
}
