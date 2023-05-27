package pl.wj.lotto.infrastructure.persistence.database.user.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Document(collection = "users")
public record UserEntity (
        @Id
        String id,
        @Indexed(unique = true)
        String username,
        String password,
        String phoneNumber,
        String emailAddress
){}