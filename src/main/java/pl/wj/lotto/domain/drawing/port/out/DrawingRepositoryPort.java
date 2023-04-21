package pl.wj.lotto.domain.drawing.port.out;

import pl.wj.lotto.domain.common.DrawingType.DrawingType;
import pl.wj.lotto.domain.drawing.model.Drawing;

import java.util.List;
import java.util.Optional;

public interface DrawingRepositoryPort {
    List<Drawing> findAllByType(DrawingType type);

    Drawing save(Drawing drawing);

    Optional<Drawing> findById(String id);
}
