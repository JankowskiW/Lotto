package pl.wj.lotto.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wj.lotto.domain.user.mapper.UserMapper;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;

@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto addUser(UserRegisterRequestDto userRegisterRequestDto) {
        String encodedPassword = passwordEncoder.encode(userRegisterRequestDto.password());
        User user = UserMapper.toUser(userRegisterRequestDto, encodedPassword);
        user = userRepositoryPort.save(user);
        return UserMapper.toUserResponseDto(user);
    }


    public UserDetails getUser(String username) {
        return userRepositoryPort.findByUsername(username);
    }
}
