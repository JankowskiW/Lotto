package pl.wj.lotto.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wj.lotto.domain.user.port.in.UserServicePort;
import pl.wj.lotto.infrastructure.security.mapper.SecurityMapper;

@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    private final UserServicePort userServicePort;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return SecurityMapper.toSecurityUser(userServicePort.getUser(username));
    }
}
