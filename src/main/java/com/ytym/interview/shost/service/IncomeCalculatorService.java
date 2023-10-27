package com.ytym.interview.shost.service;

import com.ytym.interview.shost.dto.IncomeCalculatorResultsDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeCalculatorService {
    private List<Integer> paymentsListForAnalysis;

    private final int premiumPaymentLimitStart = 100;

    public void saveInputDataForAnalysis(List<Integer> paymentsList) {
        paymentsListForAnalysis = Collections.unmodifiableList(paymentsList);
    }

    public IncomeCalculatorResultsDto calculateIncomeFor(int premiumRoomNumberRequested, int economyRoomNumberRequested) {
        TemporaryCalculationResults results = analyseInputDataFor(premiumRoomNumberRequested, economyRoomNumberRequested);

        return new IncomeCalculatorResultsDto(premiumRoomNumberRequested,
                economyRoomNumberRequested,
                0,
                0,
                0,
                0);
    }


    TemporaryCalculationResults analyseInputDataFor(int premiumRoomRequested, int economyRoomRequested) {
        return paymentsListForAnalysis.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.collectingAndThen(
                        Collectors.partitioningBy(payment -> payment < premiumPaymentLimitStart),
                        partitions -> {
                            List<Integer> economyRoomPayments = partitions.get(true);
                            List<Integer> premiumRoomPayments = partitions.get(false);

                            int economyRoomIncome = economyRoomPayments.stream()
                                    .limit(economyRoomRequested).mapToInt(Integer::intValue).sum();

                            int premiumRoomIncome = premiumRoomPayments.stream()
                                    .limit(premiumRoomRequested).mapToInt(Integer::intValue).sum();

                            int economyRoomExistsByPaymentsNumber = economyRoomPayments.size();
                            int premiumRoomExistsByPaymentsNumber = premiumRoomPayments.size();

                            int economyRoomUsed = Math.min(economyRoomExistsByPaymentsNumber, economyRoomRequested);
                            int premiumRoomUsed = Math.min(premiumRoomExistsByPaymentsNumber, premiumRoomRequested);

                            return new TemporaryCalculationResults(premiumRoomPayments, premiumRoomIncome, premiumRoomUsed, economyRoomPayments, economyRoomIncome, economyRoomUsed);
                        }
                ));
    }
}

