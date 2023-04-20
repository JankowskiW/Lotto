package pl.wj.lotto.domain.drawing.service;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.model.DrawingType;
import pl.wj.lotto.domain.drawing.port.out.DrawingRepositoryPort;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class DrawingService {
    private final DrawingRepositoryPort drawingRepositoryPort;


    public List<Drawing> getDrawings(int drawingTypeId) {
        return drawingRepositoryPort.findAllByType(getDrawingTypeById(drawingTypeId));
    }

    private DrawingType getDrawingTypeById(int id) {
        return Arrays.stream(DrawingType.values()).filter(dt -> dt.getId() == id)
                .findFirst()
                .orElseThrow(InvalidParameterException::new);
    }
}
