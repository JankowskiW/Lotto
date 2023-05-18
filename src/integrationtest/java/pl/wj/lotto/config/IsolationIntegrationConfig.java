package pl.wj.lotto.config;

import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;
import pl.wj.lotto.infrastructure.numbersreceiver.http.NumbersReceiverHttpConfig;
import pl.wj.lotto.infrastructure.numbersreceiver.http.NumbersRetrieverHttpConfigProperties;

public class IsolationIntegrationConfig extends NumbersReceiverHttpConfig {
    public IsolationIntegrationConfig(String uri, int port, int connectionTimeout, int readTimeout) {
        super(NumbersRetrieverHttpConfigProperties.builder()
                .uri(uri)
                .port(port)
                .connectionTimeout(connectionTimeout)
                .readTimeout(readTimeout)
                .build());
    }

    public NumbersReceiverPort numbersReceiverPort() {
        return numbersReceiverPort(restTemplate(restTemplateResponseErrorHandler()));
    }
}
