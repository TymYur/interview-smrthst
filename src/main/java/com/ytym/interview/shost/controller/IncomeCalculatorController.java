package com.ytym.interview.shost.controller;

import com.ytym.interview.shost.dto.IncomeCalculatorResultsDto;
import com.ytym.interview.shost.service.IncomeCalculatorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/calculate")
@RestController
@Validated
public class IncomeCalculatorController {
    private final
    IncomeCalculatorService incomeCalculatorService;

    public IncomeCalculatorController(IncomeCalculatorService incomeCalculatorService) {
        this.incomeCalculatorService = incomeCalculatorService;
    }

    @PutMapping()
    public ResponseEntity<?> loadDataToCalculateOn(
            @NotNull(message = "Required request body is missing.")
            @NotEmpty(message = "Request body should contain at least 1 entry.")
            @Size(min = 1, message = "Request body should contain at least 1 entry.")
            @RequestBody List<Integer> paymentsListForAnalysis) {
        incomeCalculatorService.saveInputDataForAnalysis(paymentsListForAnalysis);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public IncomeCalculatorResultsDto calculateIncomeFor(
            @NotNull(message = "Amount of premium rooms must be present.")
            @Positive(message = "Amount of premium rooms must be greater than 0.")
            @RequestParam("premium") Integer freePremiumRoomNumber,
            @NotNull(message = "Amount of economy rooms must be present.")
            @Positive(message = "Amount of economy rooms must be greater than 0.")
            @RequestParam("economy") Integer freeEconomyRoomNumber) {
        return incomeCalculatorService.calculateIncomeFor(freePremiumRoomNumber, freeEconomyRoomNumber);
    }
}
