package pl.wj.lotto.infrastructure.security.model.dto;

import lombok.Builder;

@Builder
public record JwtResponseDto(String username, String token) {
}
