package pl.wj.lotto.domain.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserRegisterRequestDto(
        @NotBlank(message = "{user.username.not-blank}")
        String username,
        @NotBlank(message = "{user.password.not-blank}")
        String password) {}
