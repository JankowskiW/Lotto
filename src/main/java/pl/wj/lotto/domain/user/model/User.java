package pl.wj.lotto.domain.user.model;

import lombok.Builder;

@Builder
public record User(
        String id,
        String username,
        String password
) {}
