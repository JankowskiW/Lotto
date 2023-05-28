package pl.wj.lotto.infrastructure.persistence.database.draw;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.model.dto.DrawWinningNumbersDto;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;
import pl.wj.lotto.infrastructure.persistence.database.draw.repository.DrawRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DrawDatabaseAdapter implements DrawRepositoryPort {
    private final DrawRepository drawRepository;

    public Page<Draw> findAllByType(GameType type, Pageable pageable) {
        Page<DrawEntity> drawEntitiesPage = drawRepository.findAllByType(type, pageable);
        return DrawMapper.toDrawsPage(drawEntitiesPage);
    }

    @Override
    public Draw save(Draw draw) {
        DrawEntity drawEntity = DrawMapper.toDrawEntity(draw);
        drawEntity = drawRepository.save(drawEntity);
        return DrawMapper.toDraw(drawEntity);
    }

    @Override
    public Optional<Draw> findById(String id) {
        return drawRepository.findById(id).map(DrawMapper::toDraw);
    }

    @Override
    public Optional<DrawWinningNumbersDto> findDrawWinningNumbersById(String drawId) {
        return drawRepository.findDrawWinningNumbersById(drawId);
    }

    @Override
    public List<Draw> findByTypeAndDrawDateTimeBetween(GameType gameType, LocalDateTime drawDateTimeGT, LocalDateTime drawDateTimeLTE) {
        return drawRepository.findByTypeAndDrawDateTimeBetween(gameType, drawDateTimeGT, drawDateTimeLTE);
    }
}
