package com.ytym.interview.shost.controller.dto;

public record ResultOfCalculation(Integer freePremiumRooms,
                                  Integer freeEconomyRooms,
                                  Integer usedPremiumRooms,
                                  Integer incomeFromPremiumRooms,
                                  Integer usedEconomyRooms,
                                  Integer incomeFromEconomyRooms) {
}
