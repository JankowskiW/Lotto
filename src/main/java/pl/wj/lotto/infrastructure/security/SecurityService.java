package pl.wj.lotto.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import pl.wj.lotto.domain.user.model.dto.UserLoginRequestDto;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;
import pl.wj.lotto.infrastructure.security.properties.SecurityProperties;

import java.time.*;

@RequiredArgsConstructor
public class SecurityService {
    private final Clock clock;
    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;


    public JwtResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        User user = getAuthenticatedUser(userLoginRequestDto.username(), userLoginRequestDto.password());
        return JwtResponseDto.builder()
                .token(createToken(user))
                .username(user.getUsername())
                .build();
    }

    private User getAuthenticatedUser(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        return (User) authentication.getPrincipal();
    }

    private String createToken(User user) {
        Instant now = LocalDateTime.now(clock).toInstant(ZoneOffset.UTC);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(now.plus(Duration.ofDays(securityProperties.expirationDays())))
                .withIssuer(securityProperties.issuer())
                .sign(Algorithm.HMAC256(securityProperties.secretKey()));
    }
}
