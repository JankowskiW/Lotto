package pl.wj.lotto.domain.user.port.in;

import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.model.dto.UserSecurityDto;

public interface UserServicePort {
    UserSecurityDto getUserByUsername(String username);

    UserResponseDto addUser(UserRegisterRequestDto userRegisterRequestDto);

    User getUserById(String userId);
}
