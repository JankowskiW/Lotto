package pl.wj.lotto.domain.result.model.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record DrawResultDetailsResponseDto (
    String drawId,
    LocalDateTime drawDateTime,
    Map<Integer, Integer> results) {}
