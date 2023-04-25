package pl.wj.lotto.domain.drawing.adapter;

import lombok.RequiredArgsConstructor;
import pl.wj.lotto.domain.drawing.model.dto.DrawingRequestDto;
import pl.wj.lotto.domain.drawing.model.dto.DrawingResponseDto;
import pl.wj.lotto.domain.drawing.port.in.DrawingServicePort;
import pl.wj.lotto.domain.drawing.service.DrawingService;

import java.util.List;

@RequiredArgsConstructor
public class DrawingServiceAdapter implements DrawingServicePort {
    private final DrawingService drawingService;

    @Override
    public List<DrawingResponseDto> getDrawingsByTypeId(int typeId) {
        return drawingService.getDrawingsByTypeId(typeId);
    }

    @Override
    public DrawingResponseDto addDrawing(DrawingRequestDto drawingRequestDto) {
        return drawingService.addDrawing(drawingRequestDto);
    }

    @Override
    public DrawingResponseDto getDrawingById(String id) {
        return drawingService.getDrawingById(id);
    }

}
