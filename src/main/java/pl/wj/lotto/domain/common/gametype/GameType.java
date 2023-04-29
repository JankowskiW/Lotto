package pl.wj.lotto.domain.common.gametype;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GameType {
    LOTTO(1, "Lotto"),
    Q600(2, "Quick 600"),
    EJP(3, "Eurojackpot"),
    KENO(4, "Keno");

    private final int id;
    private final String name;

    GameType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
