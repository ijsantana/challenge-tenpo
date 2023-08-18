package com.tenpo.challenge.service;

import com.tenpo.challenge.client.ExternalPercentageClient;
import com.tenpo.challenge.exception.PercentageException;
import com.tenpo.challenge.model.Percentage;
import com.tenpo.challenge.repository.PercentageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PercentageService {

    private final PercentageRepository percentageRepository;
    private final ExternalPercentageClient externalClient;

    public void updatePercentage(Integer tries){

        log.info("attempt number {}", 6 - tries);

        Optional<Percentage> lastPercentage = percentageRepository.findDistinctByStartDate();
        Double num = lastPercentage.map(Percentage::getPercentage).orElse(null);

        try {
            num = externalClient.getExternalPercentage().getNumber();
            savePercentage(num);
                    //num = Math.random() * 100;
        } catch(Exception e) {
            log.error("A external problem detected trying to get percentage: {}", e.getMessage(), e);
            if (tries > 0) {
                updatePercentage(tries - 1);
            } else {
                savePercentage(Optional.ofNullable(num)
                        .orElseThrow( () -> new RuntimeException("Do not exist provius percentage")));
            }
        }
    }

    public void savePercentage(Double num){
        try {
            log.info("New percentage is {}", num);
            Percentage percentage = new Percentage();
            percentage.setPercentage(num);
            percentage.setStartDate(LocalDateTime.now());
            Percentage percentageSaved = percentageRepository.save(percentage);
            log.info("Percentage saved is: {}", percentageSaved);
        } catch (Exception e){
            log.error("Error detected trying to save a new percentage {}", e.getMessage());
            throw new PercentageException("Fatal error detected trying to save percentage");
        }
    }

}
