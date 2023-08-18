package com.tenpo.challenge.service;

import com.tenpo.challenge.dto.OperationDto;
import com.tenpo.challenge.exception.ExternalClientException;
import com.tenpo.challenge.exception.NumberException;
import com.tenpo.challenge.model.Operation;
import com.tenpo.challenge.model.Percentage;
import com.tenpo.challenge.repository.OperationRepository;
import com.tenpo.challenge.repository.PercentageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {

    private final PercentageRepository percentageRepository;
    private final OperationRepository operationRepository;




    public Operation createNewOperation(OperationDto operationDto) {
        log.info("Creating new Operation with numbers: {}", operationDto);

        if(operationDto.getNumber1()==null || operationDto.getNumber2()==null)
            throw new NumberException("Some of the numbers are null in request");

        return operationRepository.save( new Operation()
                .withNumber1(operationDto.getNumber1())
                .withNumber2(operationDto.getNumber2())
                .withPercentage(getLastPercentage().getPercentage())
                .withDate(LocalDateTime.now())
                .calculateOperation());
    }

    public Page<Operation> getAllOperations(Integer page) {
        return operationRepository.findAll(PageRequest.of(page, 5));
    }

    private Percentage getLastPercentage(){
            return percentageRepository.findDistinctByStartDate()
                    .orElseThrow(() -> new ExternalClientException("Do not exist Percentage Client."));
    }

}
