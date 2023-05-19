package pl.wj.lotto.features;

import org.junit.jupiter.api.Test;
import pl.wj.lotto.BaseIntegrationTest;

public class UserWantsToBuyTicketAndCheckResultsIntegrationTest extends BaseIntegrationTest {
    @Test
    void shouldGenerateTicketThenInitiateDrawAndReturnResultWhenUserIsAuthenticated() {
        // step 1: Unauthenticated user tried to generate ticket and system should return FORBIDDEN(403

        // step 2: User tried to log in with wrong credentials and system should return NOT_FOUND(404)

        // step 3: User created an account and system should return CREATED(201), user id and username

        // step 4: User tried to log in with existing credentials and system should return OK(200) and JWT Token

        // step 5: User tried to generate ticket for lotto game for next two draws with invalid numbers
        // and system should return BAD_REQUEST(400)

        // step 6: User tried to generate ticket for lotto game for next two draws with valid numbers
        // and system should return CREATED(201)

        // step 7: User tried to get results for his ticket but there is no related draws

        // step 8: Scheduler generated numbers for lotto game

        // step 9: User tried to get results for his ticket and got only for first draw

        // step 10: Scheduler generated numbers for another lotto game

        // step 11: User tried to get results for his ticket and got only for both related draws

        // step 12: User tried to get results for specific draw
    }
}
