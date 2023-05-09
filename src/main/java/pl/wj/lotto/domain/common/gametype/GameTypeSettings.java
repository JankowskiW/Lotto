package pl.wj.lotto.domain.common.gametype;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;
import pl.wj.lotto.infrastructure.gametype.properties.GameTypeSettingsProperties;

@Builder
public record GameTypeSettings(
        String interval,
        GameTypeSettingsProperties settings
) {}
