package pl.wj.lotto.controller.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import pl.wj.lotto.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResultControllerExceptionsIntegrationTest extends BaseIntegrationTest {
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String RESULTS_URL = "/results";

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundStatusWhenTicketDoesNotExistInDatabase() throws Exception {
        String id = "someId";
        RequestBuilder getTicketResultsRequestBuilder = get(RESULTS_URL +"/tickets/"+id).contentType(CONTENT_TYPE);
        // when
        ResultActions getTicketResultsResult = mockMvc.perform(getTicketResultsRequestBuilder);
        // then
        getTicketResultsResult.andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldReturnNotFoundStatusWhenDrawDoesNotExistInDatabase() throws Exception {
        String id = "someId";
        RequestBuilder getDrawResultsRequestBuilder = get(RESULTS_URL +"/draws/"+id).contentType(CONTENT_TYPE);
        // when
        ResultActions getDrawResultsResult = mockMvc.perform(getDrawResultsRequestBuilder);
        // then
        getDrawResultsResult.andExpect(status().isNotFound());
    }
}
