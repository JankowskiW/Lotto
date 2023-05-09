package pl.wj.lotto.domain.common.gametype;

import lombok.Builder;
import pl.wj.lotto.domain.common.drawdatetime.model.DrawDateTimeSettings;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Builder
public record GameTypeSettingsContainer (
        Map<GameType, GameTypeSettings> settings
) {}
