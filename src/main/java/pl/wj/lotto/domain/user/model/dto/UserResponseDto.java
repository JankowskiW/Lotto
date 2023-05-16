package pl.wj.lotto.domain.user.model.dto;

import lombok.Builder;

@Builder
public record UserResponseDto(String id, String username) {
}