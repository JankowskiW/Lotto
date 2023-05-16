package pl.wj.lotto.infrastructure.security.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.user.model.dto.UserLoginRequestDto;
import pl.wj.lotto.infrastructure.security.SecurityService;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;
import pl.wj.lotto.infrastructure.security.port.in.SecurityServicePort;

@RequiredArgsConstructor
public class SecurityServiceAdapter implements SecurityServicePort {
    private final SecurityService securityService;

    @Override
    public JwtResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        return securityService.login(userLoginRequestDto);
    }
}
