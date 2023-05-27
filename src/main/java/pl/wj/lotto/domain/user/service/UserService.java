package pl.wj.lotto.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wj.lotto.domain.user.mapper.UserMapper;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.model.dto.UserSecurityDto;
import pl.wj.lotto.domain.user.port.out.UserRepositoryPort;
import pl.wj.lotto.infrastructure.application.exception.definition.ResourceNotFoundException;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

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


    public UserSecurityDto getUser(String username) {
        UserEntity userEntity = userRepositoryPort.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toUserSecurityDto(UserMapper.toUser(userEntity));
    }

    public User findById(String userId) {
        return userRepositoryPort.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
