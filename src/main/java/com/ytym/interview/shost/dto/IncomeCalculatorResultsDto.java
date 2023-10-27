package com.ytym.interview.shost.dto;

public record IncomeCalculatorResultsDto(Integer freePremiumRooms,
                                         Integer freeEconomyRooms,
                                         Integer usedPremiumRooms,
                                         Integer incomeFromPremiumRooms,
                                         Integer usedEconomyRooms,
                                         Integer incomeFromEconomyRooms) {
}
