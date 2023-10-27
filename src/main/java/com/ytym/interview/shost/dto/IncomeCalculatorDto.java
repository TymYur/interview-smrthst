package com.ytym.interview.shost.dto;

public record IncomeCalculatorDto(Integer freePremiumRooms,
                                  Integer freeEconomyRooms,
                                  Integer usedPremiumRooms,
                                  Integer incomeFromPremiumRooms,
                                  Integer usedEconomyRooms,
                                  Integer incomeFromEconomyRooms) {
}
