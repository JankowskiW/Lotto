package pl.wj.lotto.domain.draw.port.out;

import org.springframework.data.mongodb.repository.Query;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawResultDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DrawRepositoryPort {
    List<Draw> findAllByType(GameType gameType);

    Draw save(Draw draw);

    Optional<Draw> findById(String id);

    Optional<DrawResultDto> findDrawResultById(String drawId);


    @Query(value="{ 'type' : $0, 'drawDateTime' : { $gt: ?1, $lte: ?2}}", fields = "{'type' :  1, 'drawDateTime' :  1, 'numbers' :  1}")
    List<Draw> findAllByTypeAndDrawDateTime(GameType gameType, LocalDateTime generationDateTime, LocalDateTime lastDrawDateTime);
}
