package com.ytym.interview.shost.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import com.ytym.interview.shost.controller.dto.ResultOfCalculation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ProblemDetail;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWireMock({
        @ConfigureWireMock(name = "calculator-client", property = "calculator-client.url")
})
class IncomeCalculatorControllerTest {

    @InjectWireMock("calculator-client")
    private WireMockServer calculationService;

    @Autowired
    private TestRestTemplate restTemplate;

    private final String apiUrl = "/api/v1/calculate";
    private final String apiUrlPremiumQueryParamName = "premium";
    private final String apiUrlEconomyQueryParamName = "economy";

    @ParameterizedTest
    @MethodSource("provideTestParameters")
    public void testCalculateIncomeForValidPremiumAndEconomyRoomsValues(int premiumRoomsNumber, int economyRoomsNumber) {

        //Make a request to your controller using RestTemplate
        ResultOfCalculation result = restTemplate.getForObject(byUrl(premiumRoomsNumber, economyRoomsNumber), ResultOfCalculation.class);

        // Verify the response
        assertThat(result.freePremiumRooms()).isEqualTo(premiumRoomsNumber);
        assertThat(result.freeEconomyRooms()).isEqualTo(economyRoomsNumber);
        assertThat(result.usedPremiumRooms()).isEqualTo(0);
        assertThat(result.incomeFromPremiumRooms()).isEqualTo(0);
        assertThat(result.usedEconomyRooms()).isEqualTo(0);
        assertThat(result.incomeFromEconomyRooms()).isEqualTo(0);
    }

    @Test
    public void testCalculateIncomeForNotValidPremiumRoomsValue() {
        int premiumRoomsNumber = -1;
        int economyRoomsNumber = 3;

        ProblemDetail result = restTemplate.getForObject(byUrl(premiumRoomsNumber, economyRoomsNumber), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("premiumRoomNumber: amount of premium rooms must be greater than 0");
    }

    @Test
    public void testCalculateIncomeForNotValidEconomyRoomsValue() {
        int premiumRoomsNumber = 3;
        int economyRoomsNumber = -1;

        ProblemDetail result = restTemplate.getForObject(byUrl(premiumRoomsNumber, economyRoomsNumber), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("economyRoomNumber: amount of economy rooms must be greater than 0");
    }

    @Test
    public void testCalculateIncomeForNotPresentPremiumRoomsValue() {
        int economyRoomsNumber = 3;

        ProblemDetail result = restTemplate.getForObject(byUrl(null, economyRoomsNumber), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("Required request parameter 'premium' for method parameter type Integer is not present");
    }

    @Test
    public void testCalculateIncomeForNotPresentEconomyRoomsValue() {
        int premiumRoomsNumber = 3;

        ProblemDetail result = restTemplate.getForObject(byUrl(premiumRoomsNumber, null), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("Required request parameter 'economy' for method parameter type Integer is not present");
    }

    @Test
    public void testCalculateIncomeForWrongEconomyRoomsValue() {
        int premiumRoomsNumber = 3;

        ProblemDetail result = restTemplate.getForObject(byUrl(premiumRoomsNumber, 0), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("Failed to convert value of type");
    }

    @Test
    public void testCalculateIncomeForWrongPremiumRoomsValue() {
        int economyRoomsNumber = 3;

        ProblemDetail result = restTemplate.getForObject(byUrl(0, economyRoomsNumber), ProblemDetail.class);

        assertThat(result.getStatus()).isEqualTo(400);
        assertThat(result.getDetail()).contains("Failed to convert value of type");
    }


    private String byUrl(Integer premiumRoomsNumber, Integer economyRoomsNumber) {
        if (premiumRoomsNumber == null) {
            return apiUrl + "?"
                    + apiUrlEconomyQueryParamName
                    + "="
                    + economyRoomsNumber;
        }
        if (premiumRoomsNumber == 0) {
            return apiUrl + "?"
                    + apiUrlPremiumQueryParamName
                    + "="
                    + "test"
                    + "&"
                    + apiUrlEconomyQueryParamName
                    + "="
                    + economyRoomsNumber;
        }
        if (economyRoomsNumber == null) {
            return apiUrl + "?"
                    + apiUrlPremiumQueryParamName
                    + "="
                    + premiumRoomsNumber;
        }
        if (economyRoomsNumber == 0) {
            return apiUrl + "?"
                    + apiUrlPremiumQueryParamName
                    + "="
                    + premiumRoomsNumber
                    + "&"
                    + apiUrlEconomyQueryParamName
                    + "="
                    + "test";
        }
        return apiUrl + "?"
                + apiUrlPremiumQueryParamName
                + "="
                + premiumRoomsNumber
                + "&"
                + apiUrlEconomyQueryParamName
                + "="
                + economyRoomsNumber;
    }

    private static Stream<Arguments> provideTestParameters() {
        return Stream.of(
                Arguments.of(3, 3),
                Arguments.of(7, 5),
                Arguments.of(2, 7),
                Arguments.of(10, 1)
        );
    }


}