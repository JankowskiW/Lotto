package pl.wj.lotto.domain.common.gametype;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.wj.lotto.infrastructure.application.exception.definition.EnumParseException;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameTypeParser {
    public static GameType getGameTypeById(int id) {
        return Stream.of(GameType.values())
                .filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EnumParseException("Invalid GameType id"));
    }
}
