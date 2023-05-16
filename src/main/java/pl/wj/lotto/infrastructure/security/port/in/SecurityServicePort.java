package pl.wj.lotto.infrastructure.security.port.in;

import pl.wj.lotto.domain.user.model.dto.UserLoginRequestDto;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;

public interface SecurityServicePort {
    JwtResponseDto login(UserLoginRequestDto userLoginRequestDto);
}
