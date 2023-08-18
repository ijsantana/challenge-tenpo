package com.tenpo.challenge.client;

import com.tenpo.challenge.dto.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name="${feign.client.external.url}", url="${feign.client.external.url}")
public interface ExternalPercentageClient {

    @GetMapping(value = "/number/random", produces = APPLICATION_JSON_VALUE)
    ClientResponse getExternalPercentage();

}
