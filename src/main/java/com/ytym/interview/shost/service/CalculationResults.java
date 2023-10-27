package com.ytym.interview.shost.service;

import java.util.List;

public record CalculationResults(
        List<Integer> premiumPayments,
        Integer premiumSum,
        Integer premiumCount,
        List<Integer> economyPayments,
        Integer economySum,
        Integer economyCount) {
}
