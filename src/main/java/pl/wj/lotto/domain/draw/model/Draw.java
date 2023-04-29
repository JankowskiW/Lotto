package pl.wj.lotto.domain.draw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.common.numberstemplate.NumbersTemplate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Draw {
    String id;
    GameType type;
    NumbersTemplate numbers;
    LocalDateTime drawTime;
}
