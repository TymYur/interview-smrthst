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
        CalculationResults results = paymentsListForAnalysis.stream().sorted(Collections.reverseOrder())
                .collect(Collectors.teeing(
                        Collectors.partitioningBy(v -> v < premiumLimit),
                        Collectors.toList(),
                        (k, t) -> {
                            Integer economySum = k.get(true).stream().limit(economyRoomNumber).collect(Collectors.summingInt(Integer::intValue));
                            Integer premiumSum = k.get(false).stream().limit(premiumRoomNumber).collect(Collectors.summingInt(Integer::intValue));

                            Integer economyExistingNumber = Math.toIntExact(k.get(true).stream().count());
                            Integer premiumExistingNumber = Math.toIntExact(k.get(false).stream().count());

                            Integer economyUsedNumber = (economyExistingNumber < economyRoomNumber) ?  economyExistingNumber : economyRoomNumber;
                            Integer premiumUsedNumber = (premiumExistingNumber < premiumRoomNumber) ?  premiumExistingNumber : premiumRoomNumber;

                            return new CalculationResults(k.get(false),
                                    premiumSum,
                                    premiumUsedNumber,
                                    k.get(false),
                                    economySum,
                                    economyUsedNumber);
                        }));
//        Integer premiumSum = groups.get("premiumSum").get(0);
//        Integer economySum = groups.get("economySum").get(0);
//        if(groups.get("premiumCount").get(0) < premiumRoomNumber) {
//
//            List<Integer> economyList = groups.get("economyList");
//            Integer premiumSumAddons = economyList.stream().limit(premiumRoomNumber - groups.get("premiumCount").get(0)).collect(Collectors.summingInt(Integer::intValue));
//
//            premiumSum+=premiumSumAddons;
//            economySum=economyList.stream().skip(premiumRoomNumber - groups.get("premiumCount").get(0)).collect(Collectors.summingInt(Integer::intValue));;
//        }
        System.out.println(results);
        return results;
    }
}

