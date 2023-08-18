package com.tenpo.challenge.percentage;

import com.tenpo.challenge.client.ExternalPercentageClient;
import com.tenpo.challenge.dto.ClientResponse;
import com.tenpo.challenge.exception.ExternalClientException;
import com.tenpo.challenge.exception.PercentageException;
import com.tenpo.challenge.model.Percentage;
import com.tenpo.challenge.repository.PercentageRepository;
import com.tenpo.challenge.service.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Profile("Test")
@SpringBootTest
@Slf4j
public class PercentageTest {

    @InjectMocks
    PercentageService percentageService;

    @Mock
    PercentageRepository percentageRepository;

    @Mock
    ExternalPercentageClient externalPercentageClient;

    private Percentage lastPercentage;

    @BeforeEach
    public void populate() {
        lastPercentage = new Percentage(1,10d, LocalDateTime.now());
    }

    @Test
    void retryPercentageCalculationTest(){
        log.info("Runing retryable test");
        Integer retries = 5;
        doReturn(Optional.ofNullable(lastPercentage)).when(percentageRepository).findDistinctByStartDate();
        when(percentageRepository.save(any(Percentage.class))).thenReturn(lastPercentage);
        when(externalPercentageClient.getExternalPercentage()).thenThrow(PercentageException.class);
        percentageService.updatePercentage(retries);
    }

}
