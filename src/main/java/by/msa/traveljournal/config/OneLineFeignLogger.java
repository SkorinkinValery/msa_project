package by.msa.traveljournal.config;

import org.slf4j.LoggerFactory;

import feign.Logger;

public class OneLineFeignLogger extends Logger {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(
            "by.msa.traveljournal.client.DestinationGuideClient");

    @Override
    protected void log(String configKey, String format, Object... args) {
        var message = String.format(format, args)
                .replace('\r', ' ')
                .replace('\n', ' ')
                .trim();
        logger.debug("{} - {}", configKey, message);
    }
}
