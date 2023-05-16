package pl.wj.lotto.domain.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.wj.lotto.domain.user.model.User;
import pl.wj.lotto.domain.user.model.dto.UserRegisterRequestDto;
import pl.wj.lotto.domain.user.model.dto.UserResponseDto;
import pl.wj.lotto.domain.user.model.dto.UserSecurityDto;
import pl.wj.lotto.infrastructure.persistence.database.user.entity.UserEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toUser(UserRegisterRequestDto userRegisterRequestDto, String encodedPassword) {
        return User.builder()
                .username(userRegisterRequestDto.username())
                .password(encodedPassword)
                .build();
    }

    public static User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.id())
                .username(userEntity.username())
                .password(userEntity.password())
                .build();
    }

    public static UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.id())
                .username(user.username())
                .password(user.password())
                .build();
    }

    public static UserResponseDto toUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.id())
                .username(user.username())
                .build();
    }

    public static UserSecurityDto toUserSecurityDto(User user) {
        return UserSecurityDto.builder()
                .username(user.username())
                .password(user.password())
                .build();
    }
}