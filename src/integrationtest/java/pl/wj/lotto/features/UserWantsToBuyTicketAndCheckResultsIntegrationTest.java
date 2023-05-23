package pl.wj.lotto.features;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pl.wj.lotto.BaseIntegrationTest;
import pl.wj.lotto.domain.result.model.dto.TicketResultsDetailsDto;
import pl.wj.lotto.domain.ticket.model.dto.TicketResponseDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserWantsToBuyTicketAndCheckResultsIntegrationTest extends BaseIntegrationTest {

    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String JWT_REGEX = "^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$";
    private static final String USERS_URL = "/users";
    private static final String REGISTER_URL = USERS_URL + "/register";
    private static final String LOGIN_URL = USERS_URL + "/login";
    private static final String TICKETS_URL = "/tickets";
    private static final String RESULTS_URL = "/results";
    private static final String TICKET_RESULTS_URL = RESULTS_URL + "/tickets";
    private static final String DRAW_RESULTS_URL = RESULTS_URL + "/draws";
    private static final String USERNAME = "user";
    private static final String USER_PASSWORD = "password";
    private static final String CREDENTIALS_BODY = """
                {
                    "username":"%s",
                    "password":"%s"
                }
                """.formatted(USERNAME, USER_PASSWORD).trim();


    @Test
    void shouldGenerateTicketThenInitiateDrawAndReturnResultWhenUserIsAuthenticated() throws Exception {
        // step 1: Unauthenticated user tried to generate ticket and system should return FORBIDDEN(403)
        // given && when
        ResultActions failedAddTicketRequest = mockMvc.perform(get(TICKETS_URL).contentType(CONTENT_TYPE));
        // then
        failedAddTicketRequest.andExpect(status().isForbidden());

        // step 2: User tried to log in with wrong credentials and system should return NOT_FOUND(404)
        // given
        String wrongCredentialsBody = """
                    {
                        "username":"wrongUsername",
                        "password":"somePassword"
                    }
                """;
        // when
        ResultActions failedLoginAction = mockMvc.perform(post(LOGIN_URL).content(wrongCredentialsBody).contentType(CONTENT_TYPE));
        // then
        failedLoginAction.andExpect(status().isNotFound());

        // step 3: User created an account and system should return CREATED(201), user id and username
        // given && when
        ResultActions registerAction = mockMvc.perform(post(REGISTER_URL).content(CREDENTIALS_BODY).contentType(CONTENT_TYPE));
        // then
        MvcResult registerResult = registerAction.andExpect(status().isCreated()).andReturn();
        String registerResponse = registerResult.getResponse().getContentAsString();
        UserResponseDto userResponseDto = objectMapper.readValue(registerResponse, UserResponseDto.class);
        String userId = userResponseDto.id();
        assertAll(
                () -> assertThat(userResponseDto.username()).isEqualTo(USERNAME),
                () -> assertThat(userResponseDto.id()).isNotBlank()
        );

        // step 4: User tried to log in with existing credentials and system should return OK(200) and JWT Token
        // given && when
        ResultActions loginResult = mockMvc.perform(
                post(LOGIN_URL)
                        .content(CREDENTIALS_BODY)
                        .contentType(CONTENT_TYPE));
        // then
        MvcResult loginResponse = loginResult.andExpect(status().isOk()).andReturn();
        String loginResponseJson = loginResponse.getResponse().getContentAsString();
        JwtResponseDto jwtResponseDto = objectMapper.readValue(loginResponseJson, JwtResponseDto.class);
        String token = "Bearer " + jwtResponseDto.token();
        assertAll(
                () -> assertThat(jwtResponseDto.username()).isEqualTo(USERNAME),
                () -> assertThat(jwtResponseDto.token()).matches(Pattern.compile(JWT_REGEX))
        );

        // step 5: Authenticated user tried to generate ticket for lotto game for next two draws with invalid numbers
        // and system should return BAD_REQUEST(400)
        // given
        String ticketBadRequestJson = """
                {
                  "userId": "%s",
                  "gameTypeId": 1,
                  "drawsAmount": 2,
                  "numbersAutogenerated": false,
                  "mainNumbers": [100, 200, 300, 400, 500, 600]
                }
                """.formatted(userId);
        // when
        ResultActions failedAddTicketAction =
                mockMvc.perform(post(TICKETS_URL)
                        .header("Authorization", token)
                        .content(ticketBadRequestJson)
                        .contentType(CONTENT_TYPE));
        // then
        failedAddTicketAction.andExpect(status().isBadRequest());

        // step 6: User tried to generate ticket for lotto game for next two draws with valid numbers
        // and system should return CREATED(201)
        // given
        String ticketRequestJson = """
                {
                  "userId": "%s",
                  "gameTypeId": 1,
                  "drawsAmount": 2,
                  "numbersAutogenerated": false,
                  "mainNumbers": [1,2,3,4,5,6]
                }
                """.formatted(userId);
        // when
        ResultActions addTicketAction =
                mockMvc.perform(post(TICKETS_URL)
                        .header("Authorization", token)
                        .content(ticketRequestJson)
                        .contentType(CONTENT_TYPE));
        // then
        MvcResult addTicketResponse = addTicketAction.andExpect(status().isCreated()).andReturn();
        String addTicketResponseJson = addTicketResponse.getResponse().getContentAsString();
        TicketResponseDto ticketResponseDto = objectMapper.readValue(addTicketResponseJson, new TypeReference<>(){});
        addTicketAction.andExpect(status().isCreated());

        // step 7: User tried to get results for his ticket but there is no related draws
        // given
        String ticketId = ticketResponseDto.id();
        TicketResultsDetailsDto expectedResult = TicketResultsDetailsDto.builder()
                .ticketId(ticketId)
                .totalPrize(0.0)
                .results(List.of())
                .build();
        // when
        ResultActions ticketResultAction =
                mockMvc.perform(get(TICKET_RESULTS_URL + "/" + ticketId)
                        .header("Authorization", token)
                        .contentType(CONTENT_TYPE));
        // then
        MvcResult ticketResultResponse = ticketResultAction.andExpect(status().isOk()).andReturn();
        String ticketResultResponseJson = ticketResultResponse.getResponse().getContentAsString();
        TicketResultsDetailsDto result = objectMapper.readValue(ticketResultResponseJson, new TypeReference<>(){});
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);

        // step 8: Scheduler generated numbers for lotto game

        // step 9: User tried to get results for his ticket and got only for first draw

        // step 10: Scheduler generated numbers for another lotto game

        // step 11: User tried to get results for his ticket and got only for both related draws

        // step 12: User tried to get results for specific draw
    }
}
