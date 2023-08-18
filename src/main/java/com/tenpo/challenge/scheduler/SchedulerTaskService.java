package com.tenpo.challenge.scheduler;

import com.tenpo.challenge.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("SchedulerTaskService")
@RequiredArgsConstructor
@Slf4j
public class SchedulerTaskService {

    @Value("${schedule.batch.enabled}")
    private Boolean scheduledBatchenabled;

    @Value("${schedule.batch.tries}")
    private Integer tries;

    private final PercentageService percentageService;
    private static final int PROCESS_BATCH_SCHEDULED = 1800000; 	//30 minutos (esta en miliseg)

    @Scheduled(fixedRate = PROCESS_BATCH_SCHEDULED)
    public void processScheduledBatch() {
        if(scheduledBatchenabled) {
            log.info("Updating percentage value in time: {}", LocalDateTime.now());
            percentageService.updatePercentage(tries);
        } else {
            log.info("Percentage tasks are turned off");
        }
    }
}
