package pl.wj.lotto.infrastructure.application.rest.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wj.lotto.domain.user.model.dto.UserLoginRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.infrastructure.security.model.dto.JwtResponseDto;
import pl.wj.lotto.infrastructure.security.port.in.SecurityServicePort;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServicePort userServicePort;
    private final SecurityServicePort securityServicePort;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto registerUser(@Valid @RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return userServicePort.addUser(userRegisterRequestDto);
    }

    @PostMapping("/login")
    public JwtResponseDto login(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        return securityServicePort.login(userLoginRequestDto);
    }
}
