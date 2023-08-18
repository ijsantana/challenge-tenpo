package com.tenpo.challenge.operation;


import com.tenpo.challenge.dto.OperationDto;
import com.tenpo.challenge.model.Operation;
import com.tenpo.challenge.model.Percentage;
import com.tenpo.challenge.repository.OperationRepository;
import com.tenpo.challenge.repository.PercentageRepository;
import com.tenpo.challenge.service.OperationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@Profile("Test")
@SpringBootTest
@Slf4j
public class OperationTest {

    @Autowired
    OperationRepository operationRepository;

    @InjectMocks
    private OperationService operationService;

    @Mock
    private PercentageRepository percentageRepository;

    private OperationDto newOperationDto;
    private Percentage lastPercentage;

    @BeforeEach
    public void populate() {
        newOperationDto = new OperationDto(5d,15d);
        lastPercentage = new Percentage(1,10d, LocalDateTime.now());
    }


    @Test
    public void createNewOperationTest(){
        log.info("Testing the create method of operations");
        doReturn(Optional.ofNullable(lastPercentage)).when(percentageRepository).findDistinctByStartDate();
        Operation newOperation = operationService.createNewOperation(newOperationDto);
        Assertions.assertEquals(22d, newOperation.getSum());
    }


    @Test
    void test() {
        log.info("Testing the create method of operations");
        doReturn(Optional.ofNullable(lastPercentage)).when(percentageRepository).findDistinctByStartDate();
        Operation newOperation = operationService.createNewOperation(newOperationDto);
        operationRepository.save(newOperation);
        assertNotNull(newOperation.getId());
        operationRepository.delete(newOperation);
    }

}
