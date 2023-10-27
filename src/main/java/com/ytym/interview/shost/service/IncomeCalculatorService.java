package com.ytym.interview.shost.service;

import com.ytym.interview.shost.dto.IncomeCalculatorDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeCalculatorService {
    private List<Integer> paymentsListForAnalysis;

    private final int premiumLimit = 100;

    public void saveInputData(List<Integer> paymentsList) {
        paymentsListForAnalysis = Collections.unmodifiableList(paymentsList);
    }

    public IncomeCalculatorDto calculateIncomeFor(int premiumRoomNumber, int economyRoomNumber) {
        CalculationResults results = proceedCalculations(premiumRoomNumber, economyRoomNumber);


        return new IncomeCalculatorDto(premiumRoomNumber,
                economyRoomNumber,
                0,
                0,
                0,
                0);
    }


    CalculationResults proceedCalculations(int premiumRoomNumber, int economyRoomNumber) {
        CalculationResults results = paymentsListForAnalysis.stream()
                .sorted(Collections.reverseOrder())
                .collect(Collectors.collectingAndThen(
                        Collectors.partitioningBy(v -> v < premiumLimit),
                        partitions -> {
                            List<Integer> economyPartition = partitions.get(true);
                            List<Integer> premiumPartition = partitions.get(false);

                            int economySum = economyPartition.stream()
                                    .limit(economyRoomNumber)
                                    .collect(Collectors.summingInt(Integer::intValue));

                            int premiumSum = premiumPartition.stream()
                                    .limit(premiumRoomNumber)
                                    .collect(Collectors.summingInt(Integer::intValue));

                            int economyExistingNumber = economyPartition.size();
                            int premiumExistingNumber = premiumPartition.size();

                            int economyUsedNumber = Math.min(economyExistingNumber, economyRoomNumber);
                            int premiumUsedNumber = Math.min(premiumExistingNumber, premiumRoomNumber);

                            return new CalculationResults(premiumPartition, premiumSum, premiumUsedNumber, economyPartition, economySum, economyUsedNumber);
                        }
                ));
        return results;
    }
}

