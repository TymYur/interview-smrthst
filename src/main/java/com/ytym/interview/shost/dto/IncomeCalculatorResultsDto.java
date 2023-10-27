package com.ytym.interview.shost.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record IncomeCalculatorResultsDto(
        @Schema(description = "Number of premium rooms to use in calculations")
        Integer freePremiumRooms,
        @Schema(description = "Number of economy rooms to use in calculations")
        Integer freeEconomyRooms,
        @Schema(description = "Result of calculation - used premium rooms")
        Integer usedPremiumRooms,
        @Schema(description = "Result of calculation - income from premium rooms")
        Integer incomeFromPremiumRooms,
        @Schema(description = "Result of calculation - used economy rooms")
        Integer usedEconomyRooms,
        @Schema(description = "Result of calculation - income from economy rooms")
        Integer incomeFromEconomyRooms) {
}
