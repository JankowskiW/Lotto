package pl.wj.lotto.domain.user.model.dto;

import lombok.Builder;

@Builder
public record UserSecurityDto(String username, String password) {
}
