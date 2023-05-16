package pl.wj.lotto.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.wj.lotto.domain.user.port.in.UserServicePort;

@RequiredArgsConstructor
public class LoginUserDetailsService implements UserDetailsService {
    private final UserServicePort userServicePort;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userServicePort.getUser(username);
    }
}
