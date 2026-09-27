package by.msa.traveljournal.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import by.msa.traveljournal.config.DestinationGuideFeignConfig;
import by.msa.traveljournal.dto.DestinationGuideDTO;

@FeignClient(
        name = "destination-guide",
        url = "${destination-guide.url}",
        configuration = DestinationGuideFeignConfig.class)
public interface DestinationGuideClient {

    @GetMapping("/internal/destination-guides/{country}")
    DestinationGuideDTO findByCountry(@PathVariable("country") String country);
}
