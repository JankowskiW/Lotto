package pl.wj.lotto.domain.draw.port.out;

import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;

import java.util.List;
import java.util.Optional;

public interface DrawRepositoryPort {
    List<Draw> findAllByType(GameType gameType);

    Draw save(Draw draw);

    Optional<Draw> findById(String id);

    Optional<DrawResultDto> findDrawResultById(String drawId);
}
