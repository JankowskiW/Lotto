package pl.wj.lotto.domain.drawing.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.drawing.model.Drawing;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;
import pl.wj.lotto.domain.drawing.service.DrawingService;

import java.util.List;

@RequiredArgsConstructor
public class DrawingServiceAdapter implements DrawingServicePort {
    private final DrawingService drawingService;

    @Override
    public List<Drawing> getDrawings(int drawingTypeId) {
        return drawingService.getDrawings(drawingTypeId);
    }
}
