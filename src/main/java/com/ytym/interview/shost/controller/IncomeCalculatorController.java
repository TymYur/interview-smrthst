package com.ytym.interview.shost.controller;

import com.ytym.interview.shost.dto.IncomeCalculatorDto;
import com.ytym.interview.shost.service.IncomeCalculatorService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    void loadDataToCalculateOn(@NotNull @NotEmpty @Positive @Size(min = 1) @RequestBody List<Integer> paymentsListForAnalysis) {
        incomeCalculatorService.saveInputData(paymentsListForAnalysis);
    }

    @GetMapping()
    public IncomeCalculatorDto calculateIncomeFor(
            @RequestParam("premium")
            @NotNull(message = "amount of premium rooms must be present")
            @Positive(message = "amount of premium rooms must be greater than 0") Integer premiumRoomNumber,
            @RequestParam("economy")
            @NotNull(message = "amount of economy rooms must be present")
            @Positive(message = "amount of economy rooms must be greater than 0")  Integer economyRoomNumber) {
        return incomeCalculatorService.calculateIncomeFor(premiumRoomNumber, economyRoomNumber);
    }
}
