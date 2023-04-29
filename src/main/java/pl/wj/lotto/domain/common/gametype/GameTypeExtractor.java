package pl.wj.lotto.domain.common.gametype;

import java.util.stream.Stream;

public class GameTypeExtractor {
    public static GameType getGameTypeById(int id) {
        return Stream.of(GameType.values())
                .filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }

    public static GameType getGameTypeByName(String name) {
        return Stream.of(GameType.values())
                .filter(dt -> dt.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}
