package pl.wj.lotto.domain.common.gametype;

import lombok.Builder;
import pl.wj.lotto.infrastructure.gametype.properties.settings.GameTypeSettingsProperties;

import java.util.Map;

@Builder
public record GameTypeSettingsContainer(
    Map<GameType, String> intervals,
    Map<GameType, GameTypeSettingsProperties> settings
) {}
