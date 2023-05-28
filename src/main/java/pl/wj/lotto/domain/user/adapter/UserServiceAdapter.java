package pl.wj.lotto.domain.user.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.model.dto.UserSecurityDto;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.domain.user.service.UserService;

@RequiredArgsConstructor
public class UserServiceAdapter implements UserServicePort {
    private final UserService userService;

    @Override
    public UserSecurityDto getUserByUsername(String username) {
        return userService.getUserByUsername(username);
    }

    @Override
    public UserResponseDto addUser(UserRegisterRequestDto userRegisterRequestDto) {
        return userService.addUser(userRegisterRequestDto);
    }

    @Override
    public User getUserById(String userId) {
        return userService.getUserById(userId);
    }
}
