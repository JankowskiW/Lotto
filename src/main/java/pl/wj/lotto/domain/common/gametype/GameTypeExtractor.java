package pl.wj.lotto.domain.common.gametype;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GameTypeExtractor {
    public static GameType getGameTypeById(int id) {
        return Stream.of(GameType.values())
                .filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
