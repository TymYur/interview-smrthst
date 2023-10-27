package com.ytym.interview.shost.service;

import java.util.List;

public record TemporaryCalculationResults(
        List<Integer> premiumPaymentsList,
        Integer premiumRoomIncome,
        Integer premiumRoomCount,
        List<Integer> economyPaymentsList,
        Integer economyRoomIncome,
        Integer economyRoomCount) {
}
