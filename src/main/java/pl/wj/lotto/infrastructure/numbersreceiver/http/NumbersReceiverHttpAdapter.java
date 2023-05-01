package pl.wj.lotto.infrastructure.numbersreceiver.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.wj.lotto.domain.common.numbersreceiver.NumbersReceiverPort;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Log4j2
public class NumbersReceiverHttpAdapter implements NumbersReceiverPort {
    private static final String SERVICE_PATH = "/api/v1.0/random";
    private static final String SERVICE_LOWER_BOUND_PARAM = "min";
    private static final String SERVICE_UPPER_BOUND_PARAM = "max";
    private static final String SERVICE_AMOUNT_PARAM = "count";
    private static final int APPROACH_LIMIT = 10;

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<Integer> receive(int lowerBound, int upperBound, int amount) {
        Set<Integer> numbers = new HashSet<>();
        try {
            ResponseEntity<Set<Integer>> response;
            int approachNumber = 0;
            do {
                approachNumber++;
                if (approachNumber > APPROACH_LIMIT) {
                    String message = String.format("Cannot generate numbers ([%d - %d] x %d), try again.", lowerBound, upperBound, amount);
                    log.error(message);
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
                }
                response = executeGetRequest(lowerBound, upperBound, amount);
                numbers.addAll(getGeneratedNumbers(response));
            } while((long) numbers.size() < amount);
        } catch (ResourceAccessException e) {
            String message = String.format("Error while numbers generating through http client: %s", e.getMessage());
            log.error(message);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
        return numbers.stream().toList();
    }

    private ResponseEntity<Set<Integer>> executeGetRequest(int lowerBound, int upperBound, int amount) throws ResourceAccessException {
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final String url = UriComponentsBuilder.fromHttpUrl(createServiceURL())
                .queryParam(SERVICE_LOWER_BOUND_PARAM, lowerBound)
                .queryParam(SERVICE_UPPER_BOUND_PARAM, upperBound)
                .queryParam(SERVICE_AMOUNT_PARAM, amount)
                .toUriString();
        log.info(url);
        ResponseEntity<Set<Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {});

        log.info(response.getBody());
        return response;
    }

    private String createServiceURL() {
        return String.format("%s:%s%s", uri, port, SERVICE_PATH);
    }

    private Set<Integer> getGeneratedNumbers(ResponseEntity<Set<Integer>> response) {
        if (response.getBody() == null) {
            String message = "Numbers response body was null";
            log.error(message);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, message);
        }
        return new HashSet<>(response.getBody());
    }
}
