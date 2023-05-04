package pl.wj.lotto.domain.result.helper.resultchecker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ResultDto {
    int level;
    int amountOfWinners;
}
