package pl.wj.lotto.infrastructure.persistence.database.draw;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.gametype.GameType;
import pl.wj.lotto.domain.draw.mapper.DrawMapper;
import pl.wj.lotto.domain.draw.model.Draw;
import pl.wj.lotto.domain.draw.port.out.DrawRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.draw.entity.DrawEntity;
import pl.wj.lotto.infrastructure.persistence.database.draw.repository.DrawRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DrawDatabaseAdapter implements DrawRepositoryPort {
    private final DrawRepository drawRepository;

    public List<Draw> findAllByType(GameType type) {
        List<DrawEntity> drawEntities = drawRepository.findAllByType(type);
        return DrawMapper.toDraws(drawEntities);
    }

    @Override
    public Draw save(Draw draw) {
        draw.setDrawDateTime(LocalDateTime.now());
        DrawEntity drawEntity = DrawMapper.toDrawEntity(draw);
        drawEntity = drawRepository.save(drawEntity);
        return DrawMapper.toDraw(drawEntity);
    }

    @Override
    public Optional<Draw> findById(String id) {
        return drawRepository.findById(id).map(DrawMapper::toDraw);
    }
}
