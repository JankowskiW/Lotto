package pl.wj.lotto.domain.user.port.in;

import org.springframework.security.core.userdetails.UserDetails;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;

public interface UserServicePort {
    UserDetails getUser(String username);

    UserResponseDto addUser(UserRegisterRequestDto userRegisterRequestDto);
}
