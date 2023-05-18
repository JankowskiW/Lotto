package pl.wj.lotto.numbersreceiver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NumbersReceiverIntegrationTestHelper {
    private final ObjectMapper objectMapper;

    private final List<Integer> numbers = List.of(1,2,3,4,5,6);

    public String createBodyWithNumbers() {
        try {
            return objectMapper.writeValueAsString(numbers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    public List<Integer> getGeneratedNumbers() {
        return numbers;
    }
}
