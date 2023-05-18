package pl.wj.lotto.numbersreceiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.wj.lotto.config.IsolationIntegrationConfig;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NumbersReceiverHttpIsolationIntegrationTest {

    private final NumbersReceiverIntegrationTestHelper helper =
            new NumbersReceiverIntegrationTestHelper(new ObjectMapper());

    private static final String URI = "http://127.0.0.1";
    private static final int CONNECTION_TIMEOUT = 1500;
    private static final int READ_TIMEOUT = 1000;

    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 50;
    private static final int AMOUNT = 6;
    private static final String SERVICE_PATH =
            String.format("/api/v1.0/random?min=%d&max=%d&count=%d", LOWER_BOUND, UPPER_BOUND, AMOUNT);
    private static final Map.Entry<String, String> CONTENT_TYPE_HEADER =
            new AbstractMap.SimpleEntry<>("Content-Type", "application/json");

    @RegisterExtension
    public static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    NumbersReceiverPort numbersReceiverPort = new IsolationIntegrationConfig(URI, wireMockServer.getPort(),
            CONNECTION_TIMEOUT, READ_TIMEOUT).numbersReceiverPort();

    @Test
    void shouldReturnStatus200AndGeneratedNumbers() {
        // given
        wireMockServer.stubFor(WireMock.get(SERVICE_PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER.getKey(), CONTENT_TYPE_HEADER.getValue())
                        .withBody(helper.createBodyWithNumbers())));
        List<Integer> expectedResponse = helper.getGeneratedNumbers();

        // when
        List<Integer> result = numbersReceiverPort.receive(LOWER_BOUND, UPPER_BOUND, AMOUNT);

        // then
        assertThat(result)
                .isNotNull()
                .containsExactlyInAnyOrderElementsOf(expectedResponse);
    }

    @Test
    void shouldThrowExceptionWhenConnectionResetByPeer() {
        // given
        wireMockServer.stubFor(WireMock.get(SERVICE_PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER.getKey(), CONTENT_TYPE_HEADER.getValue())
                        .withFault(Fault.CONNECTION_RESET_BY_PEER)));

        // when && then
        assertThatThrownBy(() -> numbersReceiverPort.receive(LOWER_BOUND, UPPER_BOUND, AMOUNT))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error while numbers generating through http client:");
    }

    @Test
    void shouldThrowExceptionWhenReadTimeoutHasBeenReached() {
        // given
        wireMockServer.stubFor(WireMock.get(SERVICE_PATH)
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader(CONTENT_TYPE_HEADER.getKey(), CONTENT_TYPE_HEADER.getValue())
                        .withFixedDelay(READ_TIMEOUT * 2)));

        // when && then
        assertThatThrownBy(() -> numbersReceiverPort.receive(LOWER_BOUND, UPPER_BOUND, AMOUNT))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Error while numbers generating through http client:");
    }
}
