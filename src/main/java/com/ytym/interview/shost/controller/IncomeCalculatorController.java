package com.ytym.interview.shost.controller;

import com.ytym.interview.shost.dto.IncomeCalculatorResultsDto;
import com.ytym.interview.shost.service.IncomeCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Calculator",
        description = "This section contains all endpoints to manage calculations")
public class IncomeCalculatorController {
    private final
    IncomeCalculatorService incomeCalculatorService;

    public IncomeCalculatorController(IncomeCalculatorService incomeCalculatorService) {
        this.incomeCalculatorService = incomeCalculatorService;
    }

    @PutMapping()
    @Operation(
            summary = "Upload data to calculate income",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Data uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Wrong request")
            })
    public ResponseEntity<?> loadDataToCalculateOn(
            @NotNull(message = "Required request body is missing.")
            @NotEmpty(message = "Request body should contain at least 1 entry.")
            @Size(min = 1, message = "Request body should contain at least 1 entry.")
            @Parameter(name = "premium", description = "Data to calculate on", example = "[23,56,45]")
            @RequestBody List<Integer> paymentsListForAnalysis) {
        incomeCalculatorService.saveInputDataForAnalysis(paymentsListForAnalysis);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    @Operation(
            summary = "Calculate income using previously uploaded data ",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Result of calculations",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = IncomeCalculatorResultsDto.class)))
                            }),
                    @ApiResponse(responseCode = "400", description = "Wrong request")
            })
    public IncomeCalculatorResultsDto calculateIncomeFor(
            @NotNull(message = "Amount of premium rooms must be present.")
            @Positive(message = "Amount of premium rooms must be greater than 0.")
            @Parameter(name = "premium", description = "Number of premium rooms to calculate with", example = "7")
            @RequestParam("premium") Integer freePremiumRoomNumber,
            @NotNull(message = "Amount of economy rooms must be present.")
            @Positive(message = "Amount of economy rooms must be greater than 0.")
            @Parameter(name = "economy", description = "Number of economy rooms to calculate with", example = "1")
            @RequestParam("economy") Integer freeEconomyRoomNumber) {
        return incomeCalculatorService.calculateIncomeFor(freePremiumRoomNumber, freeEconomyRoomNumber);
    }
}
