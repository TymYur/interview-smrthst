package com.ytym.interview.shost.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class IncomeCalculatorServiceTest {

    private final IncomeCalculatorService incomeCalculatorService = new IncomeCalculatorService();

    private final List<Integer> inputDataForTests = List.of(23, 45, 155, 374, 22, 99, 100, 101, 115, 209);

    @Test
    void testStoreData() {
        incomeCalculatorService.saveInputDataForAnalysis(inputDataForTests);
    }

    @ParameterizedTest
    @MethodSource("provideTestParameters")
    void testCalculateData(int premiumRoomsRequested, int economyRoomsRequested, int premiumRoomsUsed, int economyRoomsUsed, int premiumRoomsIncome, int economyRoomsIncome) {

        incomeCalculatorService.saveInputDataForAnalysis(inputDataForTests);

        TemporaryCalculationResults results = incomeCalculatorService.analyseInputDataFor(premiumRoomsRequested, economyRoomsRequested);
        assertThat(results.premiumRoomCount()).isEqualTo(premiumRoomsUsed);
        assertThat(results.premiumRoomIncome()).isEqualTo(premiumRoomsIncome);
        assertThat(results.economyRoomCount()).isEqualTo(economyRoomsUsed);
        assertThat(results.economyRoomIncome()).isEqualTo(economyRoomsIncome);
    }

    private static Stream<Arguments> provideTestParameters() {
        return Stream.of(
                Arguments.of(3, 3, 3, 3, 738, 167),
                Arguments.of(7, 5, 6, 4, 1054, 189),
                Arguments.of(2, 7, 2, 4, 583, 189)//,                Arguments.of(10, 1, 7, 1, 1153, 45)
        );
    }
}