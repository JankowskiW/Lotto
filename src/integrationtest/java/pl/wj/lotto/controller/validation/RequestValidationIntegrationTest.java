package pl.wj.lotto.controller.validation;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import pl.wj.lotto.BaseIntegrationTest;
import pl.wj.lotto.infrastructure.application.exception.body.RequestValidationExceptionBody;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RequestValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnBadRequestWhenUserRegisterRequestBodyIsInvalid() throws Exception {
        // given
        String jsonRequestBody = """
                {
                }
                """.trim();
        RequestBuilder request = post("/users/register")
                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // when
        ResultActions result = mockMvc.perform(request);
        // then
        MvcResult mvcResult = result.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RequestValidationExceptionBody resultBody = objectMapper.readValue(json, RequestValidationExceptionBody.class);
        assertThat(resultBody.messages()).containsExactlyInAnyOrder(
                "password must not be blank",
                "username must not be blank",
                "email address must not be blank");
    }

    @Test
    public void shouldReturnBadRequestWhenUserLoginRequestBodyIsInvalid() throws Exception {
        // given
        String jsonRequestBody = """
                {
                }
                """.trim();
        RequestBuilder request = post("/users/login")
                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // when
        ResultActions result = mockMvc.perform(request);
        // then
        MvcResult mvcResult = result.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RequestValidationExceptionBody resultBody = objectMapper.readValue(json, RequestValidationExceptionBody.class);
        assertThat(resultBody.messages()).containsExactlyInAnyOrder(
                "password must not be blank",
                "username must not be blank");
    }

    @Test
    @WithMockUser
    public void shouldReturnBadRequestWhenTicketRequestBodyIsInvalid() throws Exception {
        // given
        String jsonRequestBody = """
                {
                }
                """.trim();
        RequestBuilder request = post("/tickets")
                .content(jsonRequestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // when
        ResultActions result = mockMvc.perform(request);
        // then
        MvcResult mvcResult = result.andExpect(status().isBadRequest()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        RequestValidationExceptionBody resultBody = objectMapper.readValue(json, RequestValidationExceptionBody.class);
        assertThat(resultBody.messages()).containsExactlyInAnyOrder(
                "draws amount must be positive number",
                "user id must not be blank",
                "game type id must be positive number");
    }
}
