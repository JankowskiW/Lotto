package pl.wj.lotto.cache.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import pl.wj.lotto.BaseIntegrationTest;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numbers.model.Numbers;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.domain.result.port.in.ResultServicePort;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RedisDrawResultsCacheIntegrationTest extends BaseIntegrationTest {
    private static final String DOCKER_IMAGE_NAME = "redis";
    private static final int  REDIS_PORT = 6379;
    private static final String CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;
    private static final String JWT_REGEX = "^([A-Za-z0-9-_=]+\\.)+([A-Za-z0-9-_=])+\\.?$";
    private static final String USERS_URL = "/users";
    private static final String REGISTER_URL = USERS_URL + "/register";
    private static final String LOGIN_URL = USERS_URL + "/login";
    private static final String RESULTS_URL_TEMPLATE = "/results/draws/%s";
    private static final String CACHE_NAME = "drawsResults";
    private static final String REQUEST_BODY = """
                {
                    "username" : "usrname",
                    "password" : "pswd"
                }
                """.trim();

    @Container
    private static final GenericContainer<?> REDIS;

    @SpyBean
    private ResultServicePort resultServicePort;

    @SpyBean
    private DrawRepositoryPort drawRepositoryPort;

    @Autowired
    private CacheManager cacheManager;

    static {
        REDIS = new GenericContainer<>(DOCKER_IMAGE_NAME).withExposedPorts(REDIS_PORT);
        REDIS.start();
    }

    @DynamicPropertySource
    public static void propertyOverride(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.redis.port", () -> REDIS.getFirstMappedPort().toString());
        registry.add("spring.cache.type", () -> CacheType.REDIS);
        registry.add("spring.cache.redis.time-to-live", () -> "PT1S");
    }

    @Test
    public void shouldCacheDrawResultsAndInvalidateWhenTTLAchieve0() throws Exception {
        // step 1: User created an account
        // given && when
        ResultActions registerAction = mockMvc.perform(post(REGISTER_URL).content(REQUEST_BODY).contentType(CONTENT_TYPE));
        // then
        registerAction.andExpect(status().isCreated());

        // step 2: User logged in
        // given && when
        ResultActions loginAction = mockMvc.perform(post(LOGIN_URL).content(REQUEST_BODY).contentType(CONTENT_TYPE));
        // then
        MvcResult mvcResult = loginAction.andExpect(status().isOk()).andReturn();
        String jsonResult = mvcResult.getResponse().getContentAsString();
        JwtResponseDto jwtResponseDto = objectMapper.readValue(jsonResult, JwtResponseDto.class);
        String responseToken = jwtResponseDto.token();
        assertAll(
                () -> assertThat(jwtResponseDto.username()).isEqualTo("usrname"),
                () -> assertThat(responseToken).matches(Pattern.compile(JWT_REGEX))
        );

        // step 3: Cached draw results response
        // given
        LocalDateTime now = LocalDateTime.now();
        GameType gameType = GameType.LOTTO;
        String token = "Bearer " + responseToken;
        String drawId = "some-draw-id";
        drawRepositoryPort.save(Draw.builder()
                        .id(drawId)
                        .type(gameType)
                        .drawDateTime(now.minusHours(1))
                        .numbers(Numbers.builder()
                                .gameType(gameType)
                                .mainNumbers(List.of(1,2,3,4,5,6))
                                .build())
                .build());
        // when
        mockMvc.perform(get(String.format(RESULTS_URL_TEMPLATE, drawId))
                .header("Authorization", token)
                .contentType(CONTENT_TYPE));
        // then
        verify(resultServicePort, times(1)).getDrawResultDetails(drawId);
        assertThat(cacheManager.getCacheNames().contains(CACHE_NAME)).isTrue();

        // step 4: Invalidated cache when TTL achieve 0
        // given
        int numberOfMethodCalling = 2;
        Duration awaitDuration = Duration.ofSeconds(5);
        Duration pollInterval = Duration.ofSeconds(1);
        RequestBuilder invalidateCacheRequestBuilder =
                get(String.format(RESULTS_URL_TEMPLATE, drawId))
                        .header("Authorization", token)
                        .contentType(CONTENT_TYPE);
        // when && then
        await()
                .atMost(awaitDuration)
                .pollInterval(pollInterval)
                .untilAsserted(() -> {
                    mockMvc.perform(invalidateCacheRequestBuilder);
                    verify(resultServicePort, atLeast(numberOfMethodCalling)).getDrawResultDetails(drawId);
                });
    }
}
