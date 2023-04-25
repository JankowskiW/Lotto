package pl.wj.lotto.infrastructure.persistence.database.drawing;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.common.drawingtype.DrawingType;
import pl.wj.lotto.domain.drawing.mapper.DrawingMapper;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;
import pl.wj.lotto.infrastructure.persistence.database.drawing.entity.DrawingEntity;
import pl.wj.lotto.infrastructure.persistence.database.drawing.repository.DrawingRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DrawingDatabaseAdapter implements DrawingRepositoryPort {
    private final DrawingRepository drawingRepository;

    public List<Drawing> findAllByType(DrawingType type) {
        List<DrawingEntity> drawingEntities = drawingRepository.findAllByType(type);
        return DrawingMapper.toDrawings(drawingEntities);
    }

    @Override
    public Drawing save(Drawing drawing) {
        drawing.setDrawingTime(LocalDateTime.now());
        DrawingEntity drawingEntity = DrawingMapper.toDrawingEntity(drawing);
        drawingEntity = drawingRepository.save(drawingEntity);
        return DrawingMapper.toDrawing(drawingEntity);
    }

    @Override
    public Optional<Drawing> findById(String id) {
        return drawingRepository.findById(id).map(DrawingMapper::toDrawing);
    }
}
