package pl.wj.lotto.domain.common.drawingtype;

import java.time.LocalDateTime;

public interface DrawingTypeHelperPort {
    LocalDateTime getNextDrawingTime(DrawingType type);
}
