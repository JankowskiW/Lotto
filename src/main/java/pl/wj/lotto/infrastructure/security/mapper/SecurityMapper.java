package pl.wj.lotto.infrastructure.security.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;
import pl.wj.lotto.domain.user.model.dto.UserSecurityDto;

import java.util.Collections;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityMapper {
    public static User toSecurityUser(UserSecurityDto userSecurityDto) {
        return new User(userSecurityDto.username(), userSecurityDto.password(), Collections.emptyList());
    }
}
