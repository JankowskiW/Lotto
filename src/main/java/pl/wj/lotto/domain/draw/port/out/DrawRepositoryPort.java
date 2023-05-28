package pl.wj.lotto.domain.draw.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DrawRepositoryPort {
    Page<Draw> findAllByType(GameType gameType, Pageable pageable);

    Draw save(Draw draw);

    Optional<Draw> findById(String id);

    Optional<DrawWinningNumbersDto> findDrawWinningNumbersById(String drawId);

    List<Draw> findByTypeAndDrawDateTimeBetween(GameType gameType, LocalDateTime drawDateTimeGT, LocalDateTime drawDateTimeLTE);
}
