package pl.wj.lotto.domain.drawing.port.in;

import pl.wj.lotto.domain.drawing.model.Drawing;

import java.util.List;

public interface DrawingServicePort {
    List<Drawing> getDrawings(int drawingTypeId);
}
