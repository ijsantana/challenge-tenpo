package com.tenpo.challenge.controller;

import com.tenpo.challenge.dto.ApiErrorRepsonse;
import com.tenpo.challenge.dto.ApiResponse;
import com.tenpo.challenge.dto.OperationDto;
import com.tenpo.challenge.exception.ExternalClientException;
import com.tenpo.challenge.exception.NumberException;
import com.tenpo.challenge.model.Operation;
import com.tenpo.challenge.service.OperationService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.Duration;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/operation")
@CrossOrigin
@Slf4j
public class OperationController {

    private final OperationService operationService;

    private final Bucket bucket;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
        Bandwidth limit = Bandwidth.classic(3, Refill.greedy(3, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @PostMapping()
    public ResponseEntity<Object> updateInformationOfOneClient(@RequestBody OperationDto operationDto){
        try {
            if (bucket.tryConsume(1)) {
                return ok(new ApiResponse<>(200, "OK", operationService.createNewOperation(operationDto)));
            } else {
                final var response = new ApiErrorRepsonse(HttpStatus.TOO_MANY_REQUESTS.value(), "Too many requests (rate: 3 request/min)");
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
        } catch(NumberException ex){
            log.error("Number Exception: {}", ex.getMessage(), ex);
            var response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        } catch(ExternalClientException ex){
            log.error("External Client Exception: {}", ex.getMessage(), ex);
            final var response = new ApiErrorRepsonse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<Operation>>> getAllOperations(@RequestParam Integer page){
        return ok(new ApiResponse<>(200, "OK", operationService.getAllOperations(page)));
    }

}
